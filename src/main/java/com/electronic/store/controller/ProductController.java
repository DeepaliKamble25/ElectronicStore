package com.electronic.store.controller;

import com.electronic.store.dto.ProductDto;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.playload.ImageResponse;
import com.electronic.store.playload.PageableResponse;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    public Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Value("${product.image.path}")
    private String productPath;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    /**
     * @author Deepali Kamble
     * @apiNote create productDto
     * @param productDto
     * @return saved ProductDto
     */
    // create
    @PostMapping("/save")
    public ResponseEntity<ProductDto> createProductDto(@Valid @RequestBody ProductDto productDto) {
        logger.info("Initiating request to create ProductDto : {}");
        ProductDto savedproductDto1 = this.productService.createProductDto(productDto);
        logger.info("Completing request to create ProductDto : {}");
        return new ResponseEntity<>(savedproductDto1, HttpStatus.CREATED);
    }

    /**
     * @autjor Deepali Kamble
     * @apiNote Update productDto by productId
     * @param productDto
     * @param productId
     * @return updated  productDto
     */
    //update
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProductDto(@Valid @RequestBody ProductDto productDto, @PathVariable String productId) {
        logger.info("Initiating request to update ProductDto : {}" +productId);
        ProductDto savedproductDto1 = this.productService.updateProductDto(productDto, productId);
        logger.info("Completing request to updateProductDto : {}"+productId );
        return new ResponseEntity<>(savedproductDto1, HttpStatus.OK);
    }

    /**
     * @author Deepali Kamble
     * @apiNote delete productDto
     * @param productId
     * @return deleted successfully message
     */
    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProductDto(@PathVariable String productId) {
        logger.info("Initiating request to delete ProductDto : {}" +productId);
        this.productService.deleteProductDto(productId);
        ApiResponse response = ApiResponse.builder().message(ApiConstant.PRODUCT_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Completing request to delete ProductDto : {}"+productId );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * @author Deepali Kamble
     * @apiNote get single ProductDto
     * @param productId
     * @return single productDto
     */

    //getsingle
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getSingleProductDto(@PathVariable String productId) {
        logger.info("Initiating request to get single ProductDto : {}" +productId);
        ProductDto singleProductDto = this.productService.getSingleProductDto(productId);
        logger.info("Completing request to get single ProductDto : {}"+productId );
        return new ResponseEntity<>(singleProductDto, HttpStatus.OK);
    }

    /**
     * @author Deepali Kamble
     * @apiNote get all
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return list of ProductDto
     */
    //getall
    @GetMapping("/getall")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating request to get all ProductDto : {}" );
        PageableResponse<ProductDto> productsDto = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completing request to get all ProductDto : {}" );
        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    /**
     * @author Deepali Kamble
     * @apiNote get live ProductDto
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return get live productDto
     */
    //getbyLivetrue
    @GetMapping("/getlive")
    public ResponseEntity<PageableResponse<ProductDto>> getLiveProductDto
    (
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating request to get Live ProductDto : {}" );
        PageableResponse<ProductDto> liveProductDto = this.productService.getAllLiveProductDto(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completing request to get Live ProductDto : {}" );
        return new ResponseEntity<>(liveProductDto, HttpStatus.OK);
    }

    /**
     * @author Deepali Kamble
     * @apiNote get ProductDto by subtitle
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    //search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProductDto

    (@PathVariable String query,
     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
     @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating request to get productDto by subtitle: {}" +query);
        PageableResponse<ProductDto> subTitleProductDto = this.productService.searchByTilte(query, pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completing request to  get productDto by subtitle: {}" +query);
        return new ResponseEntity<>(subTitleProductDto, HttpStatus.OK);
    }

    /*
     * @author Deepali Kamble
     * @apiNote  uploadimage
     * @param file
     * @param productId
     * @return saved image in ProductDto
     * @throws IOException
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam ("productimage")MultipartFile file,
            @PathVariable String productId
    ) throws IOException {
        logger.info("Initiating request to upload image: {}" +productId);
        String paths = fileService.uploadFile(file, productPath);
        ProductDto productDto = this.productService.getSingleProductDto(productId);
        productDto.setProductImageName(paths);
        ProductDto productDto1 = productService.updateProductDto(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(productDto1.getProductImageName()).message(ApiConstant.PRODUCT_Image_Name).success(true).status(HttpStatus.CREATED).build();
        logger.info("Completing request to  upload image: {}" +productId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    };

    /**
     * @author Deepali Kamble
     * @apiNote get image ProductDto
     * @param productId
     * @param response
     * @throws IOException
     */
    @GetMapping("/getimage/{productId}")
    public void serveImage(
            @PathVariable String productId,
            HttpServletResponse response
    ) throws IOException {
        logger.info("Initiating request to search image: {}" +productId);
        ProductDto productDto = this.productService.getSingleProductDto(productId);
        InputStream resource = this.fileService.getResource(productPath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Completing request to  search image: {}" +productId);

    }


}
