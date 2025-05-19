package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.StaffDTO;
import org.diplom.dormitory.mapper.StaffMapper;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.model.Staff;
import org.diplom.dormitory.repository.RoleRepository;
import org.diplom.dormitory.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Staff staff = StaffMapper.toEntity(staffDTO);
        return staffRepository.save(staff);
    }

    public List<StaffDTO> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(StaffMapper::toDTO)
                .toList();
    }
}