package com.electronic.store.controller;


import com.electronic.store.dto.RoleDto;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<RoleDto> createRoleDto(@RequestBody RoleDto roleDto){

        RoleDto roleDto1 = roleService.createRoleDto(roleDto);

        return new ResponseEntity<>(roleDto1, HttpStatus.CREATED);
    }

    @GetMapping("/get/{roleId}")
    public ResponseEntity<RoleDto> getRoleDto(@PathVariable String roleId){
        RoleDto roleDto = roleService.getById(roleId);
        return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable String roleId){
        roleService.deleteRoleDto(roleId);
        ApiResponse response = ApiResponse.builder().message(ApiConstant.Role_Deleted).success(true).status(HttpStatus.OK).build();
   return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/get")
    public ResponseEntity<List<RoleDto>> getallroles(){

        List<RoleDto> roledtos = this.roleService.findAllRole();
        return new ResponseEntity<>(roledtos, HttpStatus.OK);
    }














}
