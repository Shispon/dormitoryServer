package org.diplom.dormitory.mapper;

import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.model.Group;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.model.Role;
import org.springframework.stereotype.Component;

@Component
public class ResidentMapper {

    public static ResidentDTO toDTO(Resident resident) {
        if (resident == null) {return null;}
        ResidentDTO residentDTO = new ResidentDTO();
        residentDTO.setId(resident.getId());
        residentDTO.setFirstName(resident.getFirstName());
        residentDTO.setSecondName(resident.getSecondName());
        residentDTO.setLastName(resident.getLastName());
        residentDTO.setAge(resident.getAge());
        residentDTO.setPhoneNumber(resident.getPhoneNumber());
        residentDTO.setMail(resident.getMail());
        residentDTO.setTelegramId(resident.getTelegramId());
        residentDTO.setPhoto(resident.getPhoto());
        residentDTO.setQrCode(resident.getQrCode());
        residentDTO.setIsPresent(resident.getIsPresent());
        residentDTO.setDateCreated(resident.getDateCreated());
        residentDTO.setDateDeleted(resident.getDateDeleted());
        residentDTO.setDatePresent(resident.getDatePresent());
        residentDTO.setDateMissing(resident.getDateMissing());
        residentDTO.setRoleId(resident.getRole().getId());
        residentDTO.setGroupId(resident.getGroup().getId());
        return residentDTO;
    }

    public static Resident toEntity(ResidentDTO residentDTO) {
        if (residentDTO == null) {return null;}
        Resident resident = new Resident();
        resident.setId(residentDTO.getId());
        resident.setFirstName(residentDTO.getFirstName());
        resident.setSecondName(residentDTO.getSecondName());
        resident.setLastName(residentDTO.getLastName());
        resident.setAge(residentDTO.getAge());
        resident.setPhoneNumber(residentDTO.getPhoneNumber());
        resident.setMail(residentDTO.getMail());
        resident.setTelegramId(residentDTO.getTelegramId());
        resident.setPhoto(residentDTO.getPhoto());
        resident.setQrCode(residentDTO.getQrCode());
        resident.setIsPresent(residentDTO.getIsPresent());
        resident.setDateCreated(residentDTO.getDateCreated());
        resident.setDateDeleted(residentDTO.getDateDeleted());
        resident.setDatePresent(residentDTO.getDatePresent());
        resident.setDateMissing(residentDTO.getDateMissing());
        if (residentDTO.getRoleId() != null) {
            Role role = new Role();
            role.setId(residentDTO.getId());
            resident.setRole(role);
        }
        if (residentDTO.getGroupId() != null) {
            Group group = new Group();
            group.setId(residentDTO.getId());
            resident.setGroup(group);
        }
        return resident;
    }
}
