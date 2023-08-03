package com.electronic.store.service;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.model.Category;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    Category category;

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @BeforeEach
    public void init (){
        category = Category.builder()
                .title("ProductSoapWeb used")
                .description("wed server details working ")
                .coverImage("kkp.jpeg")
                .build();
    }
    @Test
    public void createCategoryDtoTest(){

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        CategoryDto categoryDto1 = categoryService.createCategoryDto(categoryDto);
        Assertions.assertNotNull(categoryDto1);
        Assertions.assertEquals(category.getTitle(),categoryDto1.getTitle(),"categoryDto title not match test fail !!!");

    }

    @Test
    public void updateCategoryDtoTest() {
        CategoryDto categoryDto = CategoryDto.builder()
                .title("J2EEWeb")
                .description("wed server details working ")
                .coverImage("oop.jpeg")
                .build();
        String categoryId = "asdf";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto1 = categoryService.updateCategoryDto(categoryDto, categoryId);
        Assertions.assertNotNull(categoryDto1);
        Assertions.assertEquals(category.getTitle(), categoryDto1.getTitle(), "Both  title are not equal test fail !!!");

    }
    @Test
    public void deleteCategoryDtoTest(){
        String categoryId="assdf";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        categoryService.deleteCategoryDto(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);

    }
    @Test
    public void getAllCategoriesTest(){
      Category  category1 = Category.builder()
                .title("Light used Web")
                .description("cover details working ")
                .coverImage("1kp.jpeg")
                .build();
       Category category2 = Category.builder()
                .title("Mocking Test")
                .description("wed server browers working ")
                .coverImage("kkb.jpeg")
                .build();
        List<Category>categoryList= Arrays.asList(category,category1,category2);
        Page page=new PageImpl(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> categories = categoryService.getAllCategories(1, 2, "title", "asc");
        Assertions.assertEquals(categoryList.size(),categories.getContent().size(),"Test fai;!!!");

    }
    @Test
    public void getCategoryDtoByIdTest(){
        String categoryId="hjk3";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto categoryDtoById = categoryService.getCategoryDtoById(categoryId);
        Assertions.assertNotNull(categoryDtoById);
        Assertions.assertEquals(category.getTitle(),categoryDtoById.getTitle(),"Test fail !!!");


    }
    @Test
    public  void searchByCategoryIdTest(){
        String keywords="used";
        Category  category1 = Category.builder()
                .title("Light used Web")
                .description("cover details working ")
                .coverImage("1kp.jpeg")
                .build();
        Category category2 = Category.builder()
                .title("used Mocking Test")
                .description("wed server browers working ")
                .coverImage("kkb.jpeg")
                .build();
        List<Category>categoryList= Arrays.asList(category,category1,category2);
        Mockito.when(categoryRepository.findByTitleContaining(keywords)).thenReturn(categoryList);
        List<CategoryDto> categoryDtos = categoryService.searchByCategoryId(keywords);
        Assertions.assertEquals(3,categoryDtos.size(),"test fail !!!");

    }
}
