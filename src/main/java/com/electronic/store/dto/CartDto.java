package com.electronic.store.dto;

import com.electronic.store.model.CartItem;
import com.electronic.store.model.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cardId;

    @DateTimeFormat
    private Date cardCreatedDate;


    private UserDto userDto;

    private List<CartItemDto> items = new ArrayList<>();
}
