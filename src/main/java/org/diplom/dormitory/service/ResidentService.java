package org.diplom.dormitory.service;


import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.model.Group;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.model.Role;
import org.diplom.dormitory.repository.GroupRepository;
import org.diplom.dormitory.repository.ResidentRepository;
import org.diplom.dormitory.repository.RoleRepository;
import org.diplom.dormitory.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ResidentService {
    private final ResidentRepository residentRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ResidentService(ResidentRepository residentRepository, QRCodeGenerator qrCodeGenerator, GroupRepository groupRepository, RoleRepository roleRepository) {
        this.residentRepository = residentRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.groupRepository = groupRepository;
        this.roleRepository = roleRepository;
    }

    public Resident createResident(ResidentDTO dto) {
        Resident resident = new Resident();

        resident.setFirstName(dto.getFirstName());
        resident.setSecondName(dto.getSecondName());
        resident.setLastName(dto.getLastName());
        resident.setAge(dto.getAge());
        resident.setPhoneNumber(dto.getPhoneNumber());
        resident.setMail(dto.getMail());
        resident.setTelegramId(dto.getTelegramId());
        resident.setPhoto(dto.getPhoto());

        // Генерация QR-кода
        try {
            byte[] qrCode = qrCodeGenerator.generateQRCode(dto.getTelegramId(), 200, 200);
            resident.setQrCode(qrCode);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации QR-кода", e);
        }

        resident.setIsPresent(true);
        resident.setDateCreated(LocalDateTime.now());

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        resident.setRole(role);

        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Группа не найдена"));
        resident.setGroup(group);

        return residentRepository.save(resident);
    }

}
