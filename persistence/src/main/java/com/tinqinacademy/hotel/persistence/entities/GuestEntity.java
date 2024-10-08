package com.tinqinacademy.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder

@Entity
@Table(name="guests")
public class GuestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guest_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name="phone_number",nullable = false,length=14)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "id_card_number", nullable = false, unique = true, length = 20)
    private String idCardNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "validity", nullable = false)
    private LocalDate validity;

    @Column(name = "authority", nullable = false, length = 100)
    private String authority;
}
