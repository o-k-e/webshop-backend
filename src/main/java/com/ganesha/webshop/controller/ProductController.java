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

}
