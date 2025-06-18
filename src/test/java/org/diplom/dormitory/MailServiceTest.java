package org.diplom.dormitory;

import jakarta.mail.internet.MimeMessage;
import org.diplom.dormitory.DTO.ResidentDTO;
import org.diplom.dormitory.model.Group;
import org.diplom.dormitory.service.GroupService;
import org.diplom.dormitory.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Обеспечивает инициализацию моков
class MailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private MailService mailService;

    @Test
    void testSendSimpleEmail() {
        String to = "receiver@example.com";
        String subject = "Тест";
        String text = "Привет";

        // act
        mailService.sendSimpleEmail( to, subject, text);

        // assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendModelList() throws Exception {
        // arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        ResidentDTO resident = new ResidentDTO();
        resident.setFirstName("Иван");
        resident.setLastName("Иванов");
        resident.setSecondName("Иванович");
        resident.setPhoneNumber("1234567890");
        resident.setGroupId(1);

        Group mockGroup = new Group();
        mockGroup.setGroupName("Группа 101");

        when(groupService.getGroup(1)).thenReturn(mockGroup);

        // act
        mailService.sendModelList( "to@test.com", "Subject", List.of(resident));

        // assert
        verify(mailSender, times(1)).send(any(MimeMessage.class));
    }
}
