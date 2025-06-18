package org.diplom.dormitory.mapper;


import org.diplom.dormitory.DTO.StaffDTO;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.model.Staff;
import org.springframework.stereotype.Component;


@Component
public class StaffMapper {

    public StaffMapper() {
    }

    public static StaffDTO toDTO(Staff staff) {
        if (staff == null) {
            return null;
        }
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(staff.getId());
        staffDTO.setFirstName(staff.getFirstName());
        staffDTO.setSecondName(staff.getSecondName());
        staffDTO.setLastName(staff.getLastName());
        staffDTO.setPhoneNumber(staff.getPhoneNumber());
        staffDTO.setEmail(staff.getMail());
        staffDTO.setPassword(staff.getPassword());
        staffDTO.setRoleId(staff.getRole().getId());
        return staffDTO;
    }

    public static Staff toEntity(StaffDTO staffDTO) {
        if (staffDTO == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(staffDTO.getId());
        staff.setFirstName(staffDTO.getFirstName());
        staff.setSecondName(staffDTO.getSecondName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setMail(staffDTO.getEmail());
        staff.setPassword(staffDTO.getPassword());
        if(staffDTO.getRoleId() != null) {
            Role role = new Role();
            role.setId(staffDTO.getRoleId());
            staff.setRole(role);
        }
        return staff;
    }
}
