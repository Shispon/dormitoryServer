package org.diplom.dormitory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "residents",schema = "diplom" )
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name", nullable = false)
    private String secondName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private LocalDate age;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "telegram_id", nullable = false)
    private String telegramId;

    @Column(name = "chat_id")
    private String chatId;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo")
    private byte[] photo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "qr_code")
    private byte[] qrCode;

    @Column(name = "is_present",nullable = false)
    private Boolean isPresent;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_deleted")
    private LocalDateTime dateDeleted;

    @Column(name = "date_present")
    private LocalDateTime datePresent;

    @Column(name = "date_missing")
    private LocalDateTime dateMissing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    @ToString.Exclude
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @ToString.Exclude
    private Group group;

    @ToString.Exclude
    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResidentParent> residentParents = new ArrayList<>();
}
