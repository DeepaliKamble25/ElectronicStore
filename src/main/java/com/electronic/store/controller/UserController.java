package com.electronic.store.controller;

import com.electronic.store.dto.UserDto;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.playload.ImageResponse;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.FileService;
import com.electronic.store.service.UserService;

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
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

//    create

    /**
     * @param user
     * @return userDto object
     * @author Deepali_kamble
     * @apiNote this is method to save User record in db
     */
    @PostMapping("/save")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        logger.info("Initiating request to createUser");
        UserDto userDto = this.userService.createUser(user);
        logger.info("Completing request to createUser");
        return new ResponseEntity<UserDto>(userDto, HttpStatus.CREATED);

    }

//    update

    /**
     * @param userDto
     * @param userId
     * @return userDto
     * @author Deepali Kamble
     * @apiNote this is to update user
     */
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @Valid
            @RequestBody UserDto userDto,
            @PathVariable String userId) {
        logger.info("Initiating request to updateUser" + userId);
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        logger.info("Completing request to updateUser" + userId);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }
//    delete

    /**
     * @param userId
     * @return api resonpse message
     * @author Deepali Kamble
     * @apiNote this to delete user record by userId
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) throws IOException {
        logger.info("Initiating request to deleteUser" + userId);
        this.userService.deleteUser(userId);
        ApiResponse message = ApiResponse.builder().message(ApiConstant.User_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Completing request to deleteUser" + userId);
        return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);

    }

//    get all

    /**
     * @return list of userDto
     * @author Deepali Kamble
     * @apiNote to fetch All user record
     */
    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserDto>> getAllUser
    (
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Initiating request to getAlluser");
        logger.info("Completing request to get All user");
        return new ResponseEntity<>(this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
//    get by id

    /**
     * @param userId
     * @return userDto object
     * @author Deepali kamble
     * @apiNote to get user data by id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Initiating request to deleteUser getUserById" + userId);
        UserDto getUser = this.userService.getUserById(userId);
        logger.info("Completing request to getUserById" + userId);
        return new ResponseEntity<UserDto>(getUser, HttpStatus.OK);

    }
//    get by email

    /**
     * @param email
     * @return user details
     * @author Deepali Kamble
     * @apiNote this to getUser by email.
     */
    @GetMapping("/Email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Initiating request to getUserByEmail" + email);
        UserDto updatedUser = this.userService.getUserByEmail(email);
        logger.info("Completing request to getUserById getUserByEmail" + email);
        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);

    }

//    get by keywords

    /**
     * @param keywords
     * @return users with keywords
     * @author Deepali kamble
     * @apiNote to search users record by keyword
     */

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        logger.info("Initiating request to searchUser" + keywords);
        List<UserDto> userDtos = this.userService.searchUser(keywords);
        logger.info("Completing request to searchUser" + keywords);
        return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);

    }

    /**
     * @param image
     * @param userId
     * @return
     * @author Deepali Kamble
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable String userId) throws IOException
    {
        String imageName = this.fileService.uploadFile(image, imageUploadPath);

        UserDto userDto = userService.getUserById(userId);

        userDto.setImage(imageName);

        UserDto updateduserDto = userService.updateUser(userDto, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    };
    @GetMapping("/getimage/{userId}")
     public  void serveUserImage(
             @PathVariable String userId,
             HttpServletResponse response
     ) throws IOException
     {
         UserDto userDto = this.userService.getUserById(userId);
         logger.info("Initiate User serve UserImage : {} "+userId);
         InputStream resource = fileService.getResource(imageUploadPath, userDto.getImage());

         response.setContentType(MediaType.IMAGE_JPEG_VALUE);

         StreamUtils.copy(resource,response.getOutputStream());

     }



}
