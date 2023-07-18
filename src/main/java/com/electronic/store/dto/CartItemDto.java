package com.electronic.store.dto;

import com.electronic.store.model.Cart;
import com.electronic.store.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {


    private int cartItemId;

    private Product product;

    private int quantity;

    private int totalPrice;

    private Cart cart;


}
