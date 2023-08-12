package com.electronic.store.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity
@Table(name = "order_Items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    private int quantity;
    private int totalPrice;
    @OneToOne
    @JoinColumn(name = "product_Id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="order_Id")
    private Order order;



}
