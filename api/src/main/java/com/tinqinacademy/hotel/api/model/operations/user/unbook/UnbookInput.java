package com.tinqinacademy.hotel.api.model.operations.user.unbook;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UnbookInput {
    private UUID bookId;
}
