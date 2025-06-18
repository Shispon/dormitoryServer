package org.diplom.dormitory.service;

import jakarta.mail.internet.MimeMessage;
import org.diplom.dormitory.DTO.ResidentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private GroupService groupService;

    // Отправка простого письма
    public void sendSimpleEmail( String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("WalDemar32.Polk@yandex.ru");              // От кого
        message.setTo(to);                  // Кому
        message.setSubject(subject);        // Заголовок
        message.setText(text);              // Текст письма
        mailSender.send(message);           // Отправка
    }


    public void sendModelList( String to, String subject, List<ResidentDTO> residents) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            // true = multipart (если вдруг будут вложения в будущем), UTF-8 для русских символов
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("WalDemar32.Polk@yandex.ru");
            helper.setTo(to);
            helper.setSubject(subject);

            StringBuilder html = new StringBuilder();

            // Начало HTML-таблицы
            html.append("""
                        <html>
                        <head>
                            <style>
                                table {
                                    width: 100%%;
                                    border-collapse: collapse;
                                    font-family: Arial, sans-serif;
                                }
                                th, td {
                                    border: 1px solid #dddddd;
                                    text-align: left;
                                    padding: 8px;
                                }
                                th {
                                    background-color: #f2f2f2;
                                }
                            </style>
                        </head>
                        <body>
                            <h2>Список жильцов</h2>
                            <table>
                                <tr>
                                    <th>ФИО</th>
                                    <th>Группа</th>
                                    <th>Телефон</th>
                                </tr>
                    """);

            // Данные по жильцам
            for (ResidentDTO resident : residents) {
                String fullName = resident.getLastName() + " " + resident.getFirstName() + " " + resident.getSecondName();
                String group = groupService.getGroup(resident.getGroupId()).getGroupName();
                String phone = resident.getPhoneNumber();

                html.append("<tr>")
                        .append("<td>").append(fullName).append("</td>")
                        .append("<td>").append(group).append("</td>")
                        .append("<td>").append(phone).append("</td>")
                        .append("</tr>");
            }

            // Завершение таблицы и документа
            html.append("""
                            </table>
                        </body>
                        </html>
                    """);

            helper.setText(html.toString(), true); // true = включить HTML
            mailSender.send(message);

        } catch (MessagingException | jakarta.mail.MessagingException e) {
            e.printStackTrace(); // В реальном проекте лучше использовать логгер
            // Можно выбросить своё исключение или вернуть ошибку
        }
    }


}
