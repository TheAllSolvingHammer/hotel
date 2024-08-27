package com.tinqinacademy.hotel.api.model.operations.admin.register;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class Data {
    private String firstName;
    private String lastName;
    private String phone;
    private String idNumber;
    private LocalDate validity;
    private String authority;
    private LocalDate issueDate;
    private String roomID;
}
