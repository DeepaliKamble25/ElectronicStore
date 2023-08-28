package com.electronic.store.security;

import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {


@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found));


        return user;
    }
}
