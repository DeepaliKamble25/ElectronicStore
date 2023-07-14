package com.electronic.store.service;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.playload.PageableResponse;


import java.util.List;

public interface CategoryService {


    //create
    CategoryDto createCategoryDto(CategoryDto categoryDto);


    //upload
    CategoryDto updateCategoryDto(CategoryDto categoryDto,String categoryId);

    //delete
    void deleteCategoryDto(String categoryId);


    //getall
    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir);


    //get Single

    CategoryDto getCategoryDtoById(String categoryId);
    //search by keyword

    List<CategoryDto> searchByCategoryId(String keywords);
}
