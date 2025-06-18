package org.diplom.dormitory;

import org.diplom.dormitory.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceIntegrationTest {

    @Autowired
    private MailService mailService;

    @Test
    void sendRealEmailTest() {
        String to = "vova.shpak.mm@mail.ru";  // кому отправляем
        String subject = "Проверка";
        String text = "Это тестовое письмо от JUnit!";

        mailService.sendSimpleEmail(to, subject, text);
    }
}
