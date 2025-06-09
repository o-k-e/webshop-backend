package com.ganesha.webshop.model.dto.request;

import java.util.List;

public record ProductRequest(String productName, String description, double price, List<ProductImageRequest> imageUrls) {
}
