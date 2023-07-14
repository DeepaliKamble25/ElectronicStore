package com.electronic.store.controller;

import com.electronic.store.dto.ProductDto;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    public Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // create
    @PostMapping("/save")
    public ResponseEntity<ProductDto> createProductDto(@Valid @RequestBody ProductDto productDto) {
        ProductDto savedproductDto1 = this.productService.createProductDto(productDto);

        return new ResponseEntity<>(savedproductDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProductDto(@Valid @RequestBody ProductDto productDto,@PathVariable String productId) {

        ProductDto savedproductDto1 = this.productService.updateProductDto(productDto, productId);

        return new ResponseEntity<>(savedproductDto1, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProductDto(@PathVariable String productId) {

        this.productService.deleteProductDto(productId);
        ApiResponse response = ApiResponse.builder().message(ApiConstant.PRODUCT_DELETED).success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //getsingle
    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductDto> getSingleProductDto(@PathVariable String productId) {

        ProductDto singleProductDto = this.productService.getSingleProductDto(productId);

        return new ResponseEntity<>(singleProductDto, HttpStatus.OK);
    }
    //getall
    @GetMapping("/getall")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value="pageNumber",defaultValue="0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
            )
    {

        PageableResponse<ProductDto> productsDto = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

    //getbyLivetrue
    @GetMapping("/getlive")
    public ResponseEntity<PageableResponse<ProductDto>> getLiveProductDto
    (
            @RequestParam(value="pageNumber",defaultValue="0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    )
    {

        PageableResponse<ProductDto> liveProductDto = this.productService.getAllLiveProductDto(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(liveProductDto, HttpStatus.OK);
    }

    //getBySubtitle
    @GetMapping("/gettitle/{subtitle}")
    public ResponseEntity<PageableResponse<ProductDto>> getProductDtoBysubTitle

    (       @PathVariable String subTitle,
            @RequestParam(value="pageNumber",defaultValue="0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    )
    {

        PageableResponse<ProductDto> subTitleProductDto = this.productService.searchByTilte(subTitle,pageNumber,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(subTitleProductDto, HttpStatus.OK);
    }

}
