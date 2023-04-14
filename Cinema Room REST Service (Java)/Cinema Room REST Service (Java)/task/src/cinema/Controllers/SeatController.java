package cinema.Controllers;

import cinema.DTOs.RoomDTO;
import cinema.Models.Seat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SeatController {

    final private int total_rows = 9;
    final private int total_columns = 9;

    final private List<Seat> seats = new ArrayList<>();

    public SeatController () {
        for (int row = 1; row <= total_rows; row++)
            for (int column = 1; column <= total_columns; column++)
                seats.add(new Seat(row, column));
    }

    @GetMapping("/seats")
    public RoomDTO getSeats() {
        return new RoomDTO(total_rows, total_columns, seats);
    }

}
