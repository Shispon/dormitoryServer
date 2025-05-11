package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.ResidentParentDTO;
import org.diplom.dormitory.service.ResidentParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resident_parent")
public class ResidentParentController {

    private final ResidentParentService residentParentService;

    @Autowired
    public ResidentParentController(ResidentParentService residentParentService) {
        this.residentParentService = residentParentService;
    }

    @PostMapping
    public ResponseEntity<String> createResidentParent(@RequestBody ResidentParentDTO residentParentDTO) {
        try {
            residentParentService.addParentToResident(residentParentDTO.getResidentId(), residentParentDTO.getParentId());
            return ResponseEntity.ok("Success");
        }catch(Exception e) {
            return ResponseEntity.badRequest().body("Failure");
        }
    }
}
