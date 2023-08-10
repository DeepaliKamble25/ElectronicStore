package com.electronic.store.repository;

import com.electronic.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository <CartItem,Integer>{
}
