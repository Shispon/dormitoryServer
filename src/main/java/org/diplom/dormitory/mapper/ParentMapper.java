package org.diplom.dormitory.mapper;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Role;

public class ParentMapper {

    public static ParentDTO toDTO(Parent parent){
        if(parent==null){ return null; }
        ParentDTO dto = new ParentDTO();
        dto.setId(parent.getId());
        dto.setFirstName(parent.getFirstName());
        dto.setSecondName(parent.getSecondName());
        dto.setLastName(parent.getLastName());
        dto.setMail(parent.getMail());
        dto.setPhoneNumber(parent.getPhoneNumber());
        dto.setTelegramId(parent.getTelegramId());
        dto.setChatId(parent.getChatId());
        dto.setRoleId(parent.getRole().getId());
        return dto;
    }

    public static Parent toEntity(ParentDTO dto){
        if(dto==null){ return null; }
        Parent parent = new Parent();
        parent.setId(dto.getId());
        parent.setFirstName(dto.getFirstName());
        parent.setSecondName(dto.getSecondName());
        parent.setLastName(dto.getLastName());
        parent.setMail(dto.getMail());
        parent.setPhoneNumber(dto.getPhoneNumber());
        parent.setTelegramId(dto.getTelegramId());
        parent.setChatId(dto.getChatId());
        if(dto.getRoleId()!=null){
            Role role = new Role();
            role.setId(dto.getRoleId());
            parent.setRole(role);
        }
        return parent;
    }
}
