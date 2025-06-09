package com.ganesha.webshop.model.dto.response;

import java.util.List;

public record ProductResponseWithFilteredCategories(Long id,
                                                    String productName,
                                                    String productDescription,
                                                    double price,
                                                    List<ProductImageResponse> images,
                                                    List<CategoryResponseWithIdAndName> categories
) {}
