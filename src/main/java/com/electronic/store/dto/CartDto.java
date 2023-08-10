package com.electronic.store.dto;

import com.electronic.store.model.CartItem;
import com.electronic.store.model.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDto {

    private int cardId;
    private Date cardCreatedDate;


    private UserDto userDto;

    private List<CartItemDto> items = new ArrayList<>();
}
