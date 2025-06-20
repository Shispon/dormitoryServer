package org.diplom.dormitory.controller;


import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.DTO.ResidentTelegramDTO;
import org.diplom.dormitory.mapper.ResidentMapper;
import org.diplom.dormitory.model.Resident;
import org.diplom.dormitory.service.KafkaService;
import org.diplom.dormitory.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            ResidentDTO residentDTO = residentService.getResidentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(residentDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/checkResident")
    public ResponseEntity<String> checkResident(@RequestParam String id, @RequestParam Boolean isPresent) {
        try {
            int residentId = Integer.parseInt(id);
            residentService.addPresentToResident(residentId, isPresent);

            ResidentDTO dto = residentService.getResidentById(residentId);

            kafkaService.sendResidentStatus(dto);
            return ResponseEntity.status(HttpStatus.OK).body("Данные получены и обновлены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateResident")
    public ResponseEntity<ResidentDTO> updateResident(@RequestBody ResidentDTO dto) {
        try {
            ResidentDTO residentDTO = residentService.updateResident(dto);
            return ResponseEntity.status(HttpStatus.OK).body(residentDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteResident(@RequestParam Integer id) {
        try {
            residentService.deleteResident(id);
            return ResponseEntity.status(HttpStatus.OK).body("Жилец удален");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getResidentsIsPresent")
    public ResponseEntity<List<ResidentDTO>> getResidentsIsPresent(@RequestParam Boolean isPresent) {
        try {
            List<ResidentDTO> dto = residentService.getAllResidentsPresent(isPresent);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/setChatId")
    public ResponseEntity<Boolean> setChatId(@RequestBody ResidentDTO residentDTO) {
        try {
            residentService.setChatId(residentDTO.getId(), residentDTO.getChatId());
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @GetMapping("/checkResidentByPhoneNumber")
    public ResponseEntity<ResidentTelegramDTO> checkResdientByPhoneNumber(@RequestParam String phoneNumber) {
            try {
                ResidentTelegramDTO dto = residentService.checkPhoneNumber(phoneNumber);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            }catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
    }

    @PostMapping("/sendTelegramResident")
    public ResponseEntity<Boolean> sendTelegramResident() {
        try {
            List<ResidentTelegramDTO> dto = residentService.getAllResidentsPresent();
            for (int i =0 ; i< dto.size(); i++) {
                kafkaService.sendResidentTelegram(dto.get(i));
            }
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
