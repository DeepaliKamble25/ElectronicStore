package com.electronic.store.dto;

import com.electronic.store.model.Product;
import com.electronic.store.validate.CoverImageValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;
    @NotBlank(message = "Title is required !!!")
    @Size(min=4, max=100,  message = "Title should be min 4 char and max 100")
    private String title;

    @NotBlank(message = "Description  is required  !!!")
    @Size(min=3, max=500,  message = "Description should be min 4 char and max 500")
    private String description;

    @CoverImageValid
    private String coverImage;

    private List<ProductDto> productDtos=new ArrayList<>();


}
