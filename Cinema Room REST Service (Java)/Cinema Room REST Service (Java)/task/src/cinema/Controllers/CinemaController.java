package cinema.Controllers;

import cinema.DTOs.PositionDTO;
import cinema.DTOs.RoomDTO;
import cinema.DTOs.SeatDTO;
import cinema.Models.Room;
import cinema.Models.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class CinemaController {

    private final Room room;

    public CinemaController() {
        final int total_rows = 9;
        final int total_columns = 9;
        this.room = new Room(total_rows, total_columns);
    }

    @GetMapping("/seats")
    public RoomDTO getSeats() {
        return roomToDTO(room);
    }

    @GetMapping("/seats2")
    public Room getSeats2() {
        return room;
    }

    @GetMapping("/seat/{id}")
    public Seat getSeat(@PathVariable int id) {
        return room.getSeat(id);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody PositionDTO positionDTO) {

        int row = positionDTO.row() - 1;
        int column = positionDTO.column() - 1;

        if (row >= room.getTotalRows() || column >= room.getTotalColumns()
                || column < 0 || row < 0) {
            Map<String, String> m = Map.of("error", "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        if (room.isSeatTaken(row, column)) {
            Map<String, String> m = Map.of("error", "The ticket has been already purchased!");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        room.takeSeat(row, column);
        return ResponseEntity.ok( seatToDTO(room.getSeat(row, column)) );
    }

    private SeatDTO seatToDTO(Seat seat) {
        return new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

    private RoomDTO roomToDTO(Room room) {
        List<SeatDTO> seatsDTO = new LinkedList<>();
        for(int i = 0; i < room.getTotalColumns() * room.getTotalRows(); i++)
            seatsDTO.add(seatToDTO(room.getSeat(i)));
        return new RoomDTO(room.getTotalRows(), room.getTotalColumns(), seatsDTO);
    }

}
