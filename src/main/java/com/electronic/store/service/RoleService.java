package com.electronic.store.service;

import com.electronic.store.dto.RoleDto;
import com.electronic.store.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface RoleService {


    ///create
    RoleDto createRoleDto(RoleDto roleDto);

    //getById
    RoleDto getById(String roleId);

    //delete
    void deleteRoleDto(String roleId);

    //findById
    List<RoleDto> findAllRole ();
    //assignUserrole
UserDto assignRoletoUser(String userId, String roleId);

    //assi
}
