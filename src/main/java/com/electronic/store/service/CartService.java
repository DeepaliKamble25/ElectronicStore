package com.electronic.store.service;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;

public interface CartService {



//add item to cart
    //case1:  cart user not available we will create the
//    case2: cart available add item to cart


    CartDto addToCart(String userId, AddItemToCartRequest request);

//    remove item from cart
    void removeItemFromCart(String userId,int cartItemId);
//    remove all item cart
    void removeAllItemfromCart(String userId);


}
