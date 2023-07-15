package com.electronic.store.repository;

import com.electronic.store.model.Category;
import com.electronic.store.model.Product;
import com.electronic.store.playload.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {


    Page<Product> findByTitleContaining(String subTitle,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable );

   Page<Product> findByCategory(Category category,Pageable pageable);
}