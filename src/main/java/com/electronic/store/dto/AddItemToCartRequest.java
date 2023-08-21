package com.electronic.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor

@NoArgsConstructor
public class AddItemToCartRequest {

    private String productId;
    private int quantity;
}
