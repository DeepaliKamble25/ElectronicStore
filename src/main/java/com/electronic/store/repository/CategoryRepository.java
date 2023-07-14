package com.electronic.store.repository;

import com.electronic.store.model.Category;
import com.electronic.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    List<Category> findByTitleContaining(String keywords);
}
