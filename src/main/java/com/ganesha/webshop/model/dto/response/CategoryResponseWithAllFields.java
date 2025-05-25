package com.ganesha.webshop.model.dto.response;

import java.util.List;

public record CategoryResponseWithAllFields(Long id,
                                            String categoryName,
                                            List<ProductResponse> products
                               ) {}
