package com.ganesha.webshop.model.dto.response;

import java.util.List;

public record PaginatedResponse<T>(
    List<T> content,
    int pageNumber,
    int pageSize,
    long totalElements,
    int totalPages,
    boolean last
) {}
