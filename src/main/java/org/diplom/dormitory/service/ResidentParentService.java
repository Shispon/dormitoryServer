package org.diplom.dormitory.service;

import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.model.ResidentParent;
import org.diplom.dormitory.repository.ParentRepository;
import org.diplom.dormitory.repository.ResidentParentRepository;
import org.diplom.dormitory.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
