package com.electronic.store.service;

import com.electronic.store.dto.ProductDto;
import com.electronic.store.playload.PageableResponse;

public interface ProductService {

    //create
    ProductDto createProductDto(ProductDto productDto);

    //update
    ProductDto updateProductDto(ProductDto productDto, String productId);

    //delete
    void deleteProductDto(String productId);

    //getALl
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    //getSingle

    ProductDto getSingleProductDto(String productId);

    //getLiveProduct
    PageableResponse<ProductDto> getAllLiveProductDto(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search ByTitle
    PageableResponse<ProductDto> searchByTilte(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);


    //create Product with category
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

    //assign category to product
    ProductDto updatewithCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getallCategories(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);

}
