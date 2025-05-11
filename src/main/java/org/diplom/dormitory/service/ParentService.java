package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.repository.ParentRepository;
import org.diplom.dormitory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService {
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;

    @Autowired
    public ParentService(ParentRepository parentRepository, RoleRepository roleRepository) {
        this.parentRepository = parentRepository;
        this.roleRepository = roleRepository;
    }

    public Parent createParent(ParentDTO parentDTO) {
        Parent parent = new Parent();
        parent.setFirstName(parentDTO.getFirstName());
        parent.setSecondName(parentDTO.getSecondName());
        parent.setLastName(parentDTO.getLastName());
        parent.setMail(parentDTO.getMail());
        parent.setPhoneNumber(parentDTO.getPhoneNumber());
        parent.setTelegramId(parentDTO.getTelegramId());
        Role role = roleRepository.findById(parentDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        parent.setRole(role);
        return parentRepository.save(parent);
    }
}
