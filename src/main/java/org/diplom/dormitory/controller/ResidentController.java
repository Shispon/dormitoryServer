package org.diplom.dormitory.controller;


import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.mapper.ResidentMapper;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.service.KafkaService;
import org.diplom.dormitory.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final KafkaService kafkaService;
    @Autowired
    public ResidentController(ResidentService residentService, KafkaService kafkaService) {
        this.residentService = residentService;
        this.kafkaService = kafkaService;
    }

    @PostMapping
    public ResponseEntity<ResidentDTO> createResident(@RequestBody ResidentDTO dto) {
        try {
            Resident resident = residentService.createResident(dto);
            ResidentDTO residentDTO = ResidentMapper.toDTO(resident);
            return ResponseEntity.status(HttpStatus.CREATED).body(residentDTO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResidentDTO>> getAllResidents() {
        List<ResidentDTO> residentDTOList = residentService.getAllResidents();
        return ResponseEntity.status(HttpStatus.OK).body(residentDTOList);
    }

    @GetMapping("/getResidentById")
    public ResponseEntity<ResidentDTO> getResidentById(@RequestParam Integer id) {
        try {
            Resident resident = residentService.getResidentById(id);
            ResidentDTO residentDTO = ResidentMapper.toDTO(resident);
            return ResponseEntity.status(HttpStatus.OK).body(residentDTO);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/checkResident")
    public ResponseEntity<String> checkResident(@RequestParam String id, @RequestParam Boolean isPresent) {
        try {
            int residentId = Integer.parseInt(id);
            residentService.addPresentToResident(residentId, isPresent);

            Resident resident = residentService.getResidentById(residentId);
            ResidentDTO dto = ResidentMapper.toDTO(resident);

            kafkaService.sendResidentStatus(dto);
            return ResponseEntity.status(HttpStatus.OK).body("Данные получены и обновлены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
