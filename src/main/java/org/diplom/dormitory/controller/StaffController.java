package org.diplom.dormitory.controller;

import org.diplom.dormitory.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}