package com.electronic.store.model;


import javax.persistence.*;

@Entity
@Table (name="cart_Item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @OneToOne
    private Product product;

    private int quantity;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;





}
