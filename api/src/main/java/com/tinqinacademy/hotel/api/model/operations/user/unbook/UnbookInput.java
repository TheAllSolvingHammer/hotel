package com.tinqinacademy.hotel.api.model.operations.user.unbook;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UnbookInput {
    private String bookId;
}
