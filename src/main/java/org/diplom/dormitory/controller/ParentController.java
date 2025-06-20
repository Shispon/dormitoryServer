package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.mapper.ParentMapper;
import org.diplom.dormitory.model.Parent;
import org.diplom.dormitory.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {

    private final ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    public ResponseEntity<ParentDTO> createParent(@RequestBody ParentDTO parentDTO) {
        try {
            Parent parent = parentService.createParent(parentDTO);
            ParentDTO returnDTO = ParentMapper.toDTO(parent);
            return ResponseEntity.status(HttpStatus.CREATED).body(returnDTO);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ParentDTO>> getAllParents() {
        List<ParentDTO> parentsDTO = parentService.getAllParents();
        return ResponseEntity.status(HttpStatus.OK).body(parentsDTO);
    }

    @GetMapping("/getParentById")
    public ResponseEntity<ParentDTO> getResidentById(@RequestParam Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parentService.getParentById(id));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteParentById(@RequestParam Integer id) {
        try {
            parentService.deleteParentById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Родитель удален");
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Родитель не найден");
        }
    }

    @PutMapping("/updateParent")
    public ResponseEntity<ParentDTO> updateParent(@RequestBody ParentDTO parentDTO) {
        try {
            ParentDTO dto = parentService.updateParent(parentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/setChatId")
    public ResponseEntity<Boolean> setChatId(@RequestBody ParentDTO parentDTO) {
        try {
            parentService.setChatId(parentDTO.getId(), parentDTO.getChatId());
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @GetMapping("/checkParentByPhoneNumber")
    public ResponseEntity<Boolean> checkResdientByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            parentService.checkPhoneNumber(phoneNumber);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

    }
}
