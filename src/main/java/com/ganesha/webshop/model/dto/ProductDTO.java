package com.ganesha.webshop.model.dto;

import java.util.List;

public record ProductDTO(String productName,
                         String productDescription,
                         double price,
                         List<String> categoryNames
) {}
