package org.diplom.dormitory.service;

import org.diplom.dormitory.model.Staff;
import org.diplom.dormitory.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    /**
     * Проверяет аутентификацию сотрудника и возвращает его роль.
     *
     * @param mail     Email сотрудника
     * @param password Пароль сотрудника
     * @return Роль сотрудника (roleName)
     * @throws IllegalArgumentException если сотрудник не найден или пароль неверный
     */
    public String authenticateAndGetRole(String mail, String password) {
        // Ищем сотрудника по email
        Optional<Staff> optionalStaff = staffRepository.findByMail(mail);

        if (optionalStaff.isEmpty()) {
            throw new IllegalArgumentException("Пользователь с таким email не найден.");
        }

        Staff staff = optionalStaff.get();

        // Проверяем пароль
        if (!staff.getPassword().equals(password)) {
            throw new IllegalArgumentException("Неверный пароль.");
        }

        // Возвращаем роль сотрудника
        return staff.getRole().getRoleName();
    }
}