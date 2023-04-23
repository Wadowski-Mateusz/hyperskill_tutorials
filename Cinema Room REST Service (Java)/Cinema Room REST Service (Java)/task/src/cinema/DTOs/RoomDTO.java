package cinema.DTOs;

import java.util.List;

public record RoomDTO (int total_rows, int total_columns, List<SeatDTO> available_seats) { }
