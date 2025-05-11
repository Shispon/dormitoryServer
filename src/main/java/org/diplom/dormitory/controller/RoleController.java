package org.diplom.dormitory.controller;

import org.diplom.dormitory.mapper.RoleMapper;
import org.diplom.dormitory.DTO.RoleDTO;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        List<RoleDTO> roleDTOs = roles.stream()
                .map(RoleMapper::toDTO)
                .collect(Collectors.toList());
        return roleDTOs;
    }
}
