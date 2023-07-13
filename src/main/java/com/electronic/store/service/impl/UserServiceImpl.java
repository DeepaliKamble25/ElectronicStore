package com.electronic.store.service.impl;

import com.electronic.store.dto.UserDto;
import com.electronic.store.exception.UserNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class UserServiceImpl implements UserService {

    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        //generate userId in String format
        logger.info("Initiating request to saveUser");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.modelMapper.map(userDto, User.class);
//        user.setUserId(userId);
        User saveUser = userRepository.save(user);
        logger.info("Completing request to saved");
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating request to updateUser by findById method"+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setImage(userDto.getImage());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        User updatedUser = this.userRepository.save(user);
        logger.info("Completing request to update user by findByid"+userId);
        return  this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId)  {
        logger.info("Initiating request to deleteUser"+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));

        String fullPath = imagePath + user.getImage();
//   delete user profile image

           try {
               Path path = Paths.get(fullPath);
               Files.delete(path);
           } catch(NoSuchFileException ex)
           {
        logger.info("User image not found in folder ");

        ex.printStackTrace();

           }catch (IOException e)
           {
              e.printStackTrace();
           }
        this.userRepository.delete(user);
        logger.info("Completing request to Deleted UserById "+userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating request to get AllUser");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
       // Sort sort = Sort.by(sortBy);
        //pageNumber default start from
        Pageable pageable= PageRequest.of(pageNumber-1,pageSize,sort);

        Page<User> page = this.userRepository.findAll(pageable);

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        logger.info("Completing request to get AllUser");
   return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating request to get AllUser find UserById "+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));
        logger.info("Completing request to get UserById"+userId);

        return this.modelMapper.map(user, UserDto.class);
    }
    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating request to find UserByEmail"+email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + email));
        logger.info("Completing request to find UserByEmail"+email);
        return this.modelMapper.map(user,UserDto.class);
    }
    @Override
    public List<UserDto> searchUser(String keywords) {
        logger.info("Initiating request to searchUser"+keywords);
        List<User> users = this.userRepository.findByNameContaining(keywords);
        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completing request to searchUser"+keywords);
        return userDtos;
    }


}
