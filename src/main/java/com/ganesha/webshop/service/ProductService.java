package com.ganesha.webshop.service;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.dto.request.UpdateProductRequest;
import com.ganesha.webshop.model.dto.response.PaginatedResponse;
import com.ganesha.webshop.model.dto.response.ProductIdResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.dto.response.SuccessResponse;
import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.exception.CategoryNotFoundException;
import com.ganesha.webshop.model.exception.ImageFileNotFoundException;
import com.ganesha.webshop.model.exception.ProductNotFoundException;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.repository.ProductRepository;
import com.ganesha.webshop.service.mapper.NewProductMapper;
import com.ganesha.webshop.service.mapper.ProductImageResponseMapper;
import com.ganesha.webshop.service.mapper.ProductResponseMapper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.JoinType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductResponseMapper productResponseMapper;
    private final NewProductMapper newProductMapper;
    private final ImageService imageService;
    private final FileDeletionService fileDeletionService;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductResponseMapper productResponseMapper, CategoryRepository categoryRepository, NewProductMapper newProductMapper, ImageService imageService, FileDeletionService fileDeletionService, ProductImageResponseMapper productImageResponseMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.categoryRepository = categoryRepository;
        this.newProductMapper = newProductMapper;
        this.imageService = imageService;
        this.fileDeletionService = fileDeletionService;
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return productResponseMapper.mapToProductResponseList(products);
    }

    public ProductResponse findById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return productResponseMapper.mapToProductResponse(product);
    }

    @Transactional
    public ProductIdResponse create(NewProductRequest newProductRequest) {
        List<Category> categories = newProductRequest.categoryIds().stream().map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id))).collect(Collectors.toList());
        Product product = newProductMapper.mapToProduct(newProductRequest, categories);
        productRepository.save(product);
        return new ProductIdResponse(product.getId());
    }

    @Transactional
    public SuccessResponse delete(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        List<String> fileNames = product.getImages().stream()
                .map(ProductImage::getUrl)
                .toList();
        fileDeletionService.deleteFile(fileNames);
        productRepository.delete(product);
        return new SuccessResponse(true);
    }

    /**
     * Updates an existing product with new values, categories, and optionally new images.
     *
     * @param id the ID of the product to update.
     * @param updateProductRequest the request object containing updated values.
     * @return a {@link ProductResponse} representing the updated product.
     * @throws ProductNotFoundException if the product with the given ID does not exist.
     * @throws CategoryNotFoundException if any of the provided category IDs are invalid.
     * @throws ImageFileNotFoundException if any of the new image file names are not found in storage.
     */
    @Transactional
    public ProductResponse update(long id, UpdateProductRequest updateProductRequest) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        productToUpdate.setProductName(updateProductRequest.productName());
        productToUpdate.setProductDescription(updateProductRequest.productDescription());
        productToUpdate.setPrice(updateProductRequest.price());

        List<Category> categories = findCategoriesOfProduct(updateProductRequest);
        productToUpdate.setCategories(categories);

        List<String> existingFileNames = getExistingFileNames(productToUpdate);
        List<ProductImage> newImages = extractValidNewImages(updateProductRequest, existingFileNames, productToUpdate);
        productToUpdate.getImages().addAll(newImages);

        productRepository.save(productToUpdate);

        return productResponseMapper.mapToProductResponse(productToUpdate);
    }

    private List<Category> findCategoriesOfProduct(UpdateProductRequest updateProductRequest) {

        return updateProductRequest.categoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)))
                .collect(Collectors.toList());
    }

    private List<String> getExistingFileNames(Product productToUpdate) {
        List<ProductImage> existingImages = productToUpdate.getImages();

        return existingImages.stream()
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }

    private List<ProductImage> extractValidNewImages(UpdateProductRequest updateProductRequest, List<String> existingFileNames, Product productToUpdate) {

        return updateProductRequest.imageFileNames().stream()
                .filter(existingFileName -> !existingFileNames.contains(existingFileName))
                .map(fileName  -> {
                    Optional<File> fileOptional = imageService.getImageFileIfExists(fileName);
                    if (fileOptional.isEmpty()) {
                        throw new ImageFileNotFoundException(fileName);
                    }
                    ProductImage newImage = new ProductImage();
                    newImage.setUrl(fileName);
                    newImage.setProduct(productToUpdate); //mashoz tartozo kepet hozza tud adni ujra uj id-val
                    return newImage;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a paginated list of products using the provided {@link Pageable}.
     *
     * @param pageable pagination and sorting configuration.
     * @return a {@link PaginatedResponse} containing product responses.
     */
    public PaginatedResponse<ProductResponse> getPaginatedProducts(Pageable pageable) {
        Page<Product> page = productRepository.findAll(pageable);
        return toPaginatedResponse(page);
    }

    /**
     * Searches for products using optional name query and category filter, with pagination.
     *
     * @param queryText the search query text (minimum 3 characters for name filtering).
     * @param categoryId the optional category ID to filter products by.
     * @param pageable pagination and sorting configuration.
     * @return a {@link PaginatedResponse} containing matched products.
     */
    public PaginatedResponse<ProductResponse> search(String queryText, Long categoryId, Pageable pageable) {
        final String query = (queryText == null) ? "" : queryText.trim().toLowerCase();

        Specification<Product> spec = buildSpec(query, categoryId);

        Page<Product> page = productRepository.findAll(spec, pageable);
        return toPaginatedResponse(page);
    }

    /**
     * Converts a {@link Page} of {@link Product} entities to a {@link PaginatedResponse} of DTOs.
     *
     * @param page the paginated result from the repository.
     * @return a {@link PaginatedResponse} containing product DTOs and pagination metadata.
     */
    private PaginatedResponse<ProductResponse> toPaginatedResponse(Page<Product> page) {
        List<ProductResponse> content = page.getContent().stream()
                .map(productResponseMapper::mapToProductResponse)
                .toList();

        return new PaginatedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    /**
     * Builds a dynamic JPA {@link Specification} for product search based on the query and category.
     * - If query is at least 3 characters, filters product name using prefix-matching (start of words).
     * - If categoryId is provided, filters products by category.
     *
     * @param query normalized query string (trimmed, lowercased).
     * @param categoryId optional category ID to filter by.
     * @return a composed {@link Specification} to use in search queries.
     */
    private Specification<Product> buildSpec(String query, Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicateArrayList = new ArrayList<>();

            // NÉV SZŰRÉS: csak ha legalább 3 karakteres a lekérdezés ---
            if (query != null && !query.isBlank() && query.length() >= 3) {
                var name = criteriaBuilder.lower(root.get("productName"));

                // "szó eleji" egyezés bárhol a névben:
                // 1) teljes név eleje: "füs%"
                var startsAtBeginning = criteriaBuilder.like(name, query + "%");
                // 2) szóköz utáni szó eleje: "% füs%"
                var startsAfterSpace = criteriaBuilder.like(name, "% " + query + "%");

                predicateArrayList.add(criteriaBuilder.or(startsAtBeginning, startsAfterSpace));
            }

            // --- KATEGÓRIA SZŰRÉS: mindig alkalmazható (rövid querynél is) ---
            if (categoryId != null) {
                var cat = root.join("categories", JoinType.INNER);
                predicateArrayList.add(criteriaBuilder.equal(cat.get("id"), categoryId));
                criteriaQuery.distinct(true); // JOIN miatti duplikációk elkerülése
            }

            // ha nincs predicate, az "mindent enged"
            return predicateArrayList.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicateArrayList.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }


}
