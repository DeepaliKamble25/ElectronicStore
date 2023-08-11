package com.electronic.store.repository;

import com.electronic.store.model.Order;
import com.electronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {


    List<Order> findByUser(User user);

}
