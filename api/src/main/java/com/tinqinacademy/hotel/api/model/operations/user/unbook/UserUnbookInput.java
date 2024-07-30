package com.tinqinacademy.hotel.api.model.operations.user.unbook;

import com.tinqinacademy.hotel.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class UserUnbookInput implements OperationInput {
    private UUID bookId;
}
