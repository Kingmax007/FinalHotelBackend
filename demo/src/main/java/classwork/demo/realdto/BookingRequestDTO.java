package classwork.demo.realdto;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookingRequestDTO {
    private Long userId;
    private Long roomId;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;

}