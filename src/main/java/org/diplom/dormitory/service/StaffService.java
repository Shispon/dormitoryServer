package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.StaffDTO;
import org.diplom.dormitory.mapper.StaffMapper;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.model.Staff;
import org.diplom.dormitory.repository.RoleRepository;
import org.diplom.dormitory.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository, RoleRepository roleRepository) {
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    public String authenticateAndGetRole(String mail, String password) {
        Optional<Staff> optionalStaff = staffRepository.findByMail(mail);
        if (optionalStaff.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с таким email не найден.");
        }
        Staff staff = optionalStaff.get();
        if (!staff.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный пароль.");
        }
        return staff.getRole().getRoleName();
    }

    public Staff createStaff(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setFirstName(staffDTO.getFirstName());
        staff.setSecondName(staffDTO.getSecondName());
        staff.setLastName(staffDTO.getLastName());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setMail(staffDTO.getEmail());
        staff.setPassword(staffDTO.getPassword());
        Role role = roleRepository.findById(staffDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        staff.setRole(role);
        return staff;
    }
}