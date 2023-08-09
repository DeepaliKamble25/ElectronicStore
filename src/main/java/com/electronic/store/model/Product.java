package com.electronic.store.model;

import lombok.*;


import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    private String productId;

    @Column(name = "title")
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "Discounted_Price")
    private int discountedPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "added_date")
    private Date addeddate;

    @Column(name = "live")
    private boolean live;

    @Column(name = "Stock")
    private boolean stock;

    private String productImageName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_product_Id")
    private Category category;



}
