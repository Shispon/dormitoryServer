package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.DTO.ResidentParentDTO;
import org.diplom.dormitory.mapper.ParentMapper;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.model.ResidentParent;
import org.diplom.dormitory.repository.ParentRepository;
import org.diplom.dormitory.repository.ResidentParentRepository;
import org.diplom.dormitory.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResidentParentService {
    private final ResidentRepository residentRepository;
    private final ParentRepository parentRepository;
    private final ResidentParentRepository residentParentRepository;

    @Autowired
    public ResidentParentService(ResidentRepository residentRepository,ParentRepository parentRepository,ResidentParentRepository residentParentRepository) {
        this.residentRepository = residentRepository;
        this.parentRepository = parentRepository;
        this.residentParentRepository = residentParentRepository;
    }

    public void addParentToResident(Integer residentId, Integer parentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Житель не найден"));
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Родитель не найден"));

        ResidentParent link = new ResidentParent();
        link.setResident(resident);
        link.setParent(parent);

        residentParentRepository.save(link);
    }

    @Transactional(readOnly = true)
    public List<ParentDTO> getParentsByResidentId(Integer residentId) {
        return   residentParentRepository.findParentsByResidentId(residentId).stream()
                .map(ParentMapper::toDTO)
                .toList();
    }
}
