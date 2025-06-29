package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.StaffDTO;
import org.diplom.dormitory.mapper.StaffMapper;
import org.diplom.dormitory.model.Staff;
import org.diplom.dormitory.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * Обрабатывает GET-запрос для аутентификации сотрудника.
     *
     * @param mail     Email сотрудника
     * @param password Пароль сотрудника
     * @return Роль сотрудника (roleName)
     */
    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticateStaff(
            @RequestParam String mail,
            @RequestParam String password) {

        try {
            // Получаем роль сотрудника через сервис
            String role = staffService.authenticateAndGetRole(mail, password);
            return ResponseEntity.ok(role);
        } catch (IllegalArgumentException e) {
            // Возвращаем ошибку, если аутентификация не удалась
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO) {
        try {
            Staff staff = staffService.createStaff(staffDTO);

            StaffDTO responseDro = StaffMapper.toDTO(staff);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDro);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        List<StaffDTO> staffDTOList = staffService.getAllStaff();
        return ResponseEntity.status(HttpStatus.OK).body(staffDTOList);
    }

    @PutMapping("/updateStaff")
    public ResponseEntity<StaffDTO> updateResident(@RequestBody StaffDTO dto) {
        try {
            StaffDTO residentDTO = staffService.updateStaff(dto);
            return ResponseEntity.status(HttpStatus.OK).body(residentDTO);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteResident(@RequestParam Integer id) {
        try {
            staffService.deleteStaff(id);
            return ResponseEntity.status(HttpStatus.OK).body("Пользователь удален");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}