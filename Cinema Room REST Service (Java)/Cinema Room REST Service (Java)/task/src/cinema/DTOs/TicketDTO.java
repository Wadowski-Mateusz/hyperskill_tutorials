package cinema.DTOs;

import java.util.UUID;

public record TicketDTO (UUID token, SeatDTO ticket){
}
