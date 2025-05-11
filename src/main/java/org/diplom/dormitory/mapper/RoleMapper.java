package org.diplom.dormitory.mapper;

import org.diplom.dormitory.DTO.RoleDTO;
import org.diplom.dormitory.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public static RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getRoleName());
        return roleDTO;
    }

    public static Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }
}
