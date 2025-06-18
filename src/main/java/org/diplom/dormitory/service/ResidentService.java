package org.diplom.dormitory.service;


import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.mapper.ResidentMapper;
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
import java.util.ArrayList;
import java.util.List;

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

        // Заполняем поля из DTO
        resident.setFirstName(dto.getFirstName());
        resident.setSecondName(dto.getSecondName());
        resident.setLastName(dto.getLastName());
        resident.setAge(dto.getAge());
        resident.setPhoneNumber(dto.getPhoneNumber());
        resident.setMail(dto.getMail());
        resident.setTelegramId(dto.getTelegramId());
        resident.setPhoto(dto.getPhoto());

        // Изначально QR-код не устанавливаем (оставляем null)
        resident.setIsPresent(true);
        resident.setDateCreated(LocalDateTime.now());

        // Получаем роль по id из репозитория, если не нашли — кидаем исключение
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        resident.setRole(role);

        // Аналогично получаем группу по id
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Группа не найдена"));
        resident.setGroup(group);

        // Сохраняем жителя первый раз, чтобы он получил id в базе
        resident = residentRepository.save(resident);

        // Теперь генерируем QR-код на основе сохранённого id (id уже есть после save)
        try {
            byte[] qrCode = qrCodeGenerator.generateQRCode(resident.getId().toString(), 200, 200);
            resident.setQrCode(qrCode);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации QR-кода", e);
        }

        // Сохраняем обновлённого жителя с QR-кодом
        resident = residentRepository.save(resident);

        // Возвращаем обновлённого жителя с QR-кодом
        return resident;
    }


    public List<ResidentDTO> getAllResidents() {
        List<Resident> residents = residentRepository.findAll();
        return residents.stream()
                .map(ResidentMapper::toDTO)
                .toList();
    }

    public List<ResidentDTO> getAllResidentsPresent(Boolean present) {
        List<Resident> residents = residentRepository.findAll();
        List<ResidentDTO> dto = new ArrayList<>();

        for (Resident resident : residents) {
            Boolean isPresent = resident.getIsPresent();
            Boolean isDeleted = resident.getIsDeleted();

            if (present.equals(isPresent) && (isDeleted == null || !isDeleted)) {
                dto.add(ResidentMapper.toDTO(resident));
            }
        }

        return dto;
    }




    public ResidentDTO getResidentById(Integer id) {
        return ResidentMapper.toDTO(residentRepository.findById(id).orElseThrow(null));
    }

    public void addPresentToResident(Integer id, Boolean present) {
        try {
            Resident resident = residentRepository.findById(id).orElseThrow(null);
            resident.setIsPresent(present);
            if (present) {
                resident.setDatePresent(LocalDateTime.now());
            }else
                resident.setDateMissing(LocalDateTime.now());
            residentRepository.save(resident);
        }catch (NullPointerException e) {
            e.getMessage();
        }
    }

    public void deleteResident(Integer id) {
        Resident resident = residentRepository.findById(id).orElseThrow(null);
        resident.setIsDeleted(true);
        resident.setDateDeleted(LocalDateTime.now());
        residentRepository.save(resident);
    }

    public ResidentDTO updateResident(ResidentDTO dto) {
        if (dto == null) return null;

        Resident resident = residentRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Жилец не найден"));

        resident.setFirstName(dto.getFirstName());
        resident.setSecondName(dto.getSecondName());
        resident.setLastName(dto.getLastName());
        resident.setAge(dto.getAge());
        resident.setPhoneNumber(dto.getPhoneNumber());
        resident.setMail(dto.getMail());
        resident.setTelegramId(dto.getTelegramId());
        resident.setPhoto(dto.getPhoto());

        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Группа не найдена"));
        resident.setGroup(group);

        return ResidentMapper.toDTO(residentRepository.save(resident));
    }




}
