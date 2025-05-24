package com.ganesha.webshop.model.dto.response;

import java.util.List;

public record ProductResponse(long id,
                              String productName,
                              String productDescription,
                              double price
) {}
