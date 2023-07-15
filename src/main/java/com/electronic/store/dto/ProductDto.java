package com.electronic.store.dto;

import com.electronic.store.model.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private  String productId;
    @NotBlank(message = "title required !!!")
    @Size(min=4, max=50,message = "min char should be 5 max char 50 required !!!")
    private String title;

    @NotBlank(message = "Description required !!!")
    @Size(min=4, max=500,message = "min char should be 5 max char 50 required !!!")
    private String description;


    private int price;


    private int quantity;


    private int discountedPrice;

    private Date addeddate;

    private boolean live;

    private boolean stock;

    private String productImageName;

    private CategoryDto category;
}
