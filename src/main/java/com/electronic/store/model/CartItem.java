package com.electronic.store.model;


import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name="cart_Item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_CartItem_Id")
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_CartIt_Id")
    private Cart cart;





}
