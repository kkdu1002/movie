package movie.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UpdateReservationDto {

    private Long scheduleId;

    private String seatNumber;

    private LocalDateTime reservationTime;
}
