package org.diplom.dormitory.controller;

import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage( @RequestParam String to, @RequestParam String subject, @RequestParam String text ) {
        try {
            mailService.sendSimpleEmail( to, subject, text);
            return ResponseEntity.status(HttpStatus.OK).body("Сообщение отправлено");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/sendList")
    public ResponseEntity<String> sendList( @RequestParam String to, @RequestParam String subject, @RequestBody List<ResidentDTO> residents) {
        try {
            mailService.sendModelList(to, subject, residents);
            return ResponseEntity.status(HttpStatus.OK).body("Список пользователей отправлен");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
