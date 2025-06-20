package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.mapper.ParentMapper;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.repository.ParentRepository;
import org.diplom.dormitory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ParentDTO> getAllParents() {
        List<Parent> parents = parentRepository.findAll();
        return parents.stream()
                .map(ParentMapper::toDTO)
                .toList();
    }

    public ParentDTO getParentById(Integer id) {
        return ParentMapper.toDTO(parentRepository.findById(id).orElseThrow(null));
    }

    public void deleteParentById(Integer id) {
        parentRepository.deleteById(id);
    }

    public ParentDTO updateParent(ParentDTO parentDTO) {
        if(parentDTO == null) {return null;}
        Parent parent = parentRepository.findById(parentDTO.getId()).orElseThrow(null);
        parent.setFirstName(parentDTO.getFirstName());
        parent.setSecondName(parentDTO.getSecondName());
        parent.setLastName(parentDTO.getLastName());
        parent.setMail(parentDTO.getMail());
        parent.setPhoneNumber(parentDTO.getPhoneNumber());
        parent.setTelegramId(parentDTO.getTelegramId());
        return ParentMapper.toDTO(parentRepository.save(parent));
    }

    public List<ParentDTO> getParentsByResidentId(Integer residentId) {
       return parentRepository.findParentsByResidentId(residentId).stream()
               .map(ParentMapper::toDTO)
               .toList();
    }

    public void setChatId(Integer id, String chatId) {
        Parent parent = parentRepository.findById(id).orElseThrow(null);
        parent.setChatId(chatId);
        parentRepository.save(parent);
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return parentRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
}
