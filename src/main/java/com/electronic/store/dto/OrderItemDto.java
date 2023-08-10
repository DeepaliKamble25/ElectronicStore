package com.electronic.store.dto;

import com.electronic.store.model.Order;
import com.electronic.store.model.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;
    private int totalPrice;

    private Product product;

    private Order order;

}
