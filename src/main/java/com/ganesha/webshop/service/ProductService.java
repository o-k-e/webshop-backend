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

    //Todo: check if it's going to be needed or not
    public PaginatedResponse<ProductResponse> getPaginatedProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(productResponseMapper::mapToProductResponse)
                .toList();

        return new PaginatedResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }

    public PaginatedResponse<ProductResponse> search(String queryText, Long categoryId, Pageable pageable) {
        Specification<Product> spec = buildSpec(queryText, categoryId);
        Page<Product> page = productRepository.findAll(spec, pageable);

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

    private Specification<Product> buildSpec(String queryText, Long categoryId) {
        return (root, cq, cb) -> {
            List<jakarta.persistence.criteria.Predicate> preds = new ArrayList<>();

            if (queryText != null && !queryText.isBlank()) {
                String like = "%" + queryText.toLowerCase() + "%";
                preds.add(cb.like(cb.lower(root.get("productName")), like));
            }

            if (categoryId != null) {
                var cat = root.join("categories", JoinType.INNER);
                preds.add(cb.equal(cat.get("id"), categoryId));
                cq.distinct(true); // duplikáció elkerülése JOIN miatt
            }

            return cb.and(preds.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }


}
