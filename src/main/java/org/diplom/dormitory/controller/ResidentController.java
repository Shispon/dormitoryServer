package org.diplom.dormitory.controller;


import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.mapper.ResidentMapper;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    @Autowired
    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    public ResponseEntity<ResidentDTO> createResident(@RequestBody ResidentDTO dto) {
        try {
            // 1. Сохраняем нового жильца (маппинг DTO -> Entity происходит внутри сервиса)
            Resident resident = residentService.createResident(dto);

            // 2. Маппим сохранённую сущность обратно в DTO
            ResidentDTO residentDTO = ResidentMapper.toDTO(resident);

            // 3. Возвращаем результат с кодом 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(residentDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
