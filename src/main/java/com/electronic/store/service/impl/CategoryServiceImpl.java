package com.electronic.store.service.impl;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.model.Category;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.CategoryRepository;
import com.electronic.store.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {

    public static Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.profile.coverImage.path}")
    private String coverImagePath;
    @Override
    public CategoryDto createCategoryDto(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(saveCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategoryDto(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.Resource_Not_Found_Exception));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }


    @Override
    public void deleteCategoryDto(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.Resource_Not_Found_Exception));
           this.categoryRepository.delete(category);

        String fullCoverpath = coverImagePath + category.getCoverImage();
        try {
            Path coverImagepath = Paths.get(fullCoverpath);
            Files.delete(coverImagepath);
        } catch(NoSuchFileException ex)
        {
            logger.info("Category image cover not found in folder ");

            ex.printStackTrace();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        this.categoryRepository.delete(category);
        logger.info("Completing request to Deleted category bycategoryId: {} "+categoryId);
    }

    @Override
    public PageableResponse <CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating request to get All categories: {} ");

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> page = this.categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> response = Helper.getPageableResponse(page, CategoryDto.class);

        logger.info("Completing request to get All categories: {} ");

        return response;
    }

    @Override
    public CategoryDto getCategoryDtoById(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.Resource_Not_Found_Exception));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searchByCategoryId(String keywords) {
        logger.info("Initiating request to searchUser"+keywords);

        List<Category> categories = this.categoryRepository.findByTitleContaining(keywords);
        List<CategoryDto> categoryDtos = categories.stream().map(cate -> this.modelMapper.map(cate, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
