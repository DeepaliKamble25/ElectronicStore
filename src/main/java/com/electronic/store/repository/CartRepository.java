package com.electronic.store.repository;

import com.electronic.store.model.Cart;
import com.electronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
