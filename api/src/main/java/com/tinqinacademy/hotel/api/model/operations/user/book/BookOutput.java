package com.tinqinacademy.hotel.api.model.operations.user.book;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder(toBuilder = true)
public class BookOutput {
    private String message;
}
