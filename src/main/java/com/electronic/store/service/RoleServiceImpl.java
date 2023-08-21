package com.electronic.store.service;

import com.electronic.store.dto.RoleDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.model.Role;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.repository.RoleRepository;
import com.electronic.store.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleRepository roleRepository;
@Autowired
  private UserRepository userRepository;

private ModelMapper modelMapper;

      @Override
    public RoleDto createRoleDto( RoleDto roleDto) {
          Role role = this.modelMapper.map(roleDto, Role.class);
          Role savedrole = roleRepository.save(role);
          return this.modelMapper.map(savedrole, RoleDto.class);
    }

    @Override
    public RoleDto getById(String roleId) {
         Role role= roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException(ApiConstant.Role_NULL));
        return this.modelMapper.map(role,RoleDto.class);
    }

    @Override
    public void deleteRoleDto(String roleId) {
        Role role= roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException(ApiConstant.Role_NULL));
       roleRepository.delete(role);
    }

    @Override
    public List<RoleDto> findAllRole() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> rolesDtos = roles.stream().map(o -> this.modelMapper.map(o, RoleDto.class)).collect(Collectors.toList());
        return rolesDtos;
    }

    @Override
    public UserDto assignRoletoUser(String userId, String roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found));
        Role role= roleRepository.findById(roleId).orElseThrow(()->new ResourceNotFoundException(ApiConstant.Role_NULL));
        Set<Role> setRoles = user.getRoles();
        setRoles.add(role);
        user.setRoles(setRoles);
        User savedUser = userRepository.save(user);
        return this.modelMapper.map(savedUser, UserDto.class);
    }
}
