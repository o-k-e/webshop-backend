package com.ganesha.webshop.controller;

import com.ganesha.webshop.model.dto.request.NewProductRequest;
import com.ganesha.webshop.model.dto.request.UpdateProductRequest;
import com.ganesha.webshop.model.dto.response.PaginatedResponse;
import com.ganesha.webshop.model.dto.response.ProductIdResponse;
import com.ganesha.webshop.model.dto.response.ProductResponse;
import com.ganesha.webshop.model.dto.response.SuccessResponse;
import com.ganesha.webshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductIdResponse createProduct(@RequestBody @Valid NewProductRequest newProductRequest) {
        return productService.create(newProductRequest);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        return productService.update(id, updateProductRequest);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse deleteProduct(@PathVariable long id) {
        return productService.delete(id);
    }

    /**
     * Searches for products based on optional filters and pagination parameters.
     * <p>
     * Supports the following options:
     * <ul>
     *   <li><strong>query</strong> – Text-based search on product name (case-insensitive, partial match supported).</li>
     *   <li><strong>categoryId</strong> – Optional category filter; only returns products belonging to the specified category.</li>
     *   <li><strong>page</strong> – Page number to return (zero-based index). Default is 0.</li>
     *   <li><strong>size</strong> – Number of items per page. Default is 20.</li>
     *   <li><strong>sort</strong> – Sorting criteria, e.g., {@code "price,asc"}, {@code "productName,desc"}, {@code "id,asc"}.</li>
     * </ul>
     *
     * @param query       optional text query to search in product names
     * @param categoryId  optional category ID to filter by
     * @param page        page number to return (starting from 0), default is 0
     * @param size        number of items per page, default is 20
     * @param sort        sorting rules (field and direction), default is {"id", "asc"}
     * @return            a {@link PaginatedResponse} containing a page of {@link ProductResponse} items
     */
    @GetMapping("/search")
    public PaginatedResponse<ProductResponse> searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return productService.search(query, categoryId, pageable);
    }

    @GetMapping("/paginated")
    public PaginatedResponse<ProductResponse> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        return productService.getPaginatedProducts(pageable);
    }

    private Pageable buildPageable(int page, int size, String[] sort) {
        if (sort.length == 2) {
            String sortField = sort[0];
            Sort.Direction direction = Sort.Direction.fromString(sort[1]);
            return PageRequest.of(page, size, Sort.by(direction, sortField));
        }
        return PageRequest.of(page, size);
    }

    @GetMapping("/suggestions")
    public List<String> getSuggestions(@RequestParam String query) {
        return productService.getSuggestions(query);
    }
}