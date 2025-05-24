package com.ganesha.webshop.model.dto.response;

import java.util.List;

public record CategoryResponse(long id,
                               String categoryName,
                               List<ProductResponse> products) {
}
