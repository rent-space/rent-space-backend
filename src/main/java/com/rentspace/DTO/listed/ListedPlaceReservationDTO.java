package com.rentspace.DTO.listed;

import com.rentspace.model.reservation.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListedPlaceReservationDTO {

    private Status status;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private String title;
    private List<String> media;

}
