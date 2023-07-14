package com.electronic.store.model;

import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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








}
