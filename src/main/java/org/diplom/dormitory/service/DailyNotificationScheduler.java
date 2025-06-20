package org.diplom.dormitory.service;

import org.diplom.dormitory.DTO.ParentDTO;
import org.diplom.dormitory.DTO.ResidentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyNotificationScheduler {

    private final KafkaTemplate<String, ParentDTO> parentTemplate;
    private final KafkaTemplate<String, ResidentDTO> residentTemplate;

    private final ParentService parentService;
    private final ResidentService residentService;

    @Autowired
    public DailyNotificationScheduler(KafkaTemplate<String, ParentDTO> parentTemplate,
                                      KafkaTemplate<String, ResidentDTO> residentTemplate, ParentService parentService, ResidentService residentService) {
        this.parentTemplate = parentTemplate;
        this.residentTemplate = residentTemplate;
        this.parentService = parentService;
        this.residentService = residentService;
    }

    @Scheduled(cron = "0 0 21 * * *") // каждый день в 21:00
    public void sendNotifications() {
        List<ResidentDTO> absentResidents = getAbsentResidents();

        for (ResidentDTO resident : absentResidents) {
            // Отправка уведомления жильцу
            residentTemplate.send("notify-residents", resident);

            // Получение родителей и отправка уведомлений им
            List<ParentDTO> parents = getParentByResident(resident);
            if (parents != null) {
                for (ParentDTO parent : parents) {
                    parentTemplate.send("notify-parents", parent);
                }
            }
        }
    }


    // Заглушки
    private List<ResidentDTO> getAbsentResidents() {

        return residentService.getAllResidentsPresent(false);
    }

    private List<ParentDTO> getParentByResident(ResidentDTO r) {

        return parentService.getParentsByResidentId(r.getId());
    }
}
