package com.electronic.store.controller;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.dto.ProductDto;
import com.electronic.store.playload.*;
import com.electronic.store.service.CategoryService;
import com.electronic.store.service.FileService;
import com.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private ProductService productService;
    @Value("${category.profile.coverImage.path}")
    private String coverImagepath;

    @Autowired
    private FileService fileService;

    @Autowired
    private CategoryService categoryService;

    /**
     * @author Deepali
     * @param categoryDto
     * @return
     */

    //create
    @PostMapping("/save")
    public ResponseEntity<CategoryDto> createCategoryDto(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Initiating request to save CategoryDto : {}");

        CategoryDto categoryDto1 = this.categoryService.createCategoryDto(categoryDto);
        logger.info("Completing request to save CategoryDto : {}");

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/updated/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategoryDto(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String categoryId) {
        logger.info("Initiating request to update  CategoryDto : {}" + categoryId);

        CategoryDto categoryDto1 = this.categoryService.updateCategoryDto(categoryDto, categoryId);
        logger.info("Completing request to update CategoryDto : {}" + categoryId);

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {
        logger.info("Initiating request to delete CategoryDto : {}" + categoryId);

        this.categoryService.deleteCategoryDto(categoryId);
        ApiResponse apiResponse = ApiResponse.builder().message(ApiConstant.Category_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Completing request to delete CategoryDto : {}" + categoryId);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    //getall
    @GetMapping("/getall")
    public ResponseEntity<PageableResponse<CategoryDto>> getallCategoryDto(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating request to getAllcategories: {} ");
        return new ResponseEntity<>(this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

    }

    ;

    //getByid
    @GetMapping("/getcategory/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryDtoById(@PathVariable String categoryId) {
        logger.info("Initiating request to getCategoryDtoById : {}" + categoryId);
        CategoryDto categoryDto = this.categoryService.getCategoryDtoById(categoryId);
        logger.info("Completing request to getCategoryDtoById : {}" + categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    //getKeywords
    @GetMapping("/{keywords}")
    public ResponseEntity<List<CategoryDto>> searchCategoriesByTitle(@PathVariable String keywords) {
        logger.info("Initiating request to searchCategoriesByTitle: {}" + keywords);

        List<CategoryDto> categoryDtos = this.categoryService.searchByCategoryId(keywords);
        logger.info("Completing request to searchCategoriesByTitle: {}" + keywords);

        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);

    }

    //upload coverImage
    @PostMapping("/coverimage/{categoryId}")
    public ResponseEntity<CoverImageResponse> uploadCoverImage(
            @RequestParam("coverImage") MultipartFile coverImage,
            @PathVariable String categoryId
    ) throws IOException {
        logger.info("Initiating request to uploadCoverImage: {}" + categoryId);

        String coverImage1 = this.fileService.uploadFile(coverImage, coverImagepath);
        CategoryDto categoryDto = this.categoryService.getCategoryDtoById(categoryId);

        categoryDto.setCoverImage(coverImage1);

        CategoryDto updateCategoryDto = this.categoryService.updateCategoryDto(categoryDto, categoryId);

        CoverImageResponse coverImageResponse = CoverImageResponse.builder().coverImageName(coverImage1).success(true).status(HttpStatus.OK).build();
        logger.info("Completing request  to uploadCoverImage: {}" + categoryId);
        return new ResponseEntity<>(coverImageResponse, HttpStatus.OK);

    }

    //    servecoverImage
    @GetMapping("/serveimage/{categoryId}")
    public void serveCoverImage(
            @PathVariable String categoryId,
            HttpServletResponse response
    ) throws IOException {
        logger.info("Initiating request to serveCoverImage: {}" + categoryId);
        CategoryDto categoryDto = this.categoryService.getCategoryDtoById(categoryId);

        InputStream resource = this.fileService.getResource(coverImagepath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Completing request  to serveCoverImage: {}" + categoryId);

    }
    //http:localhost:9090/categories/{categoryId}/products
    //create category with product
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductDtowithCategory(
            @PathVariable String categoryId,
           @Valid @RequestBody ProductDto productDto
    ) {
        logger.info("Initiating request to createProductDtowithCategory: {}" + categoryId);

        ProductDto savedWithCategory = productService.createWithCategory(productDto, categoryId);
        logger.info("Completing request  to createProductDtowithCategory: {}" + categoryId);

        return new ResponseEntity<>(savedWithCategory,HttpStatus.CREATED);

    }
    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryInProduct(
            @PathVariable String categoryId,
            @PathVariable String productId

    ){
        logger.info("Initiating request to updateCategoryInProduct: {}" + categoryId +productId);
        ProductDto updateCategory = this.productService.updatewithCategory( productId,categoryId);
        logger.info("Completing request  to updateCategoryInProduct: {}" + categoryId  +productId);
        return  new ResponseEntity<>(updateCategory,HttpStatus.OK);
    };

    @GetMapping("/getproducts/{categoryId}")
    public ResponseEntity<PageableResponse<ProductDto>>getAllProductwithCategories(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        logger.info("Initiating request to getAllProductwithCategories: {}" + categoryId +categoryId);
        PageableResponse<ProductDto> getallCategories = this.productService.getallCategories(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(getallCategories,HttpStatus.OK);

    }
}
