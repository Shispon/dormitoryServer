package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.DTO.ResidentParentDTO;
import org.diplom.dormitory.service.ResidentParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/parents_of_resident/{residentId}")
    public ResponseEntity<List<ParentDTO>> getParentsByResidentId(@PathVariable Integer residentId) {
        try {
            List<ParentDTO> parents = residentParentService.getParentsByResidentId(residentId);
            return ResponseEntity.ok(parents);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
