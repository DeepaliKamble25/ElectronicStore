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
@Table(name="products")
public class Product {

    @Id
    private  String productId;

    private String title;
    @Column(length=1000)
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addeddate;
    private boolean live;
    private boolean stock;


    private String productImageName;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "category_product_Id")
    private Category category;







}
