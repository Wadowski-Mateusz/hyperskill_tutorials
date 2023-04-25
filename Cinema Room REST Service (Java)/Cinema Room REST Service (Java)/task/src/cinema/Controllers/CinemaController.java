package cinema.Controllers;

import cinema.DTOs.*;
import cinema.Models.Room;
import cinema.Models.Seat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    public ResponseEntity<?> purchaseTicket(@RequestBody PositionDTO positionDTO) {

        int row = positionDTO.row() - 1;
        int column = positionDTO.column() - 1;


        if( !(0 <= row && row < room.getTotalRows())
                || !(0 <= column && column < room.getTotalColumns())) {
            Map<String, String> m = Map.of("error", "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        if (room.isSeatTaken(row, column)) {
            Map<String, String> m = Map.of("error", "The ticket has been already purchased!");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        room.takeSeat(row, column);
        TicketDTO ticketDTO = seatToTicketDTO(room.getSeat(row, column));
        return ResponseEntity.ok(ticketDTO);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody ReturnTokenDTO token) {
        Seat seat = room.getSeats().stream()
                .filter(s -> s.getToken() != null)
                .filter(s -> s.getToken().equals(token.token()))
                .findFirst()
                .orElse(null);

        if (seat == null) {
            Map<String, String> m = Map.of("error", "Wrong token!");
            return new ResponseEntity<>(m, HttpStatus.BAD_REQUEST);
        }

        seat.setToken(null);
        return ResponseEntity.ok(new ReturnedTicketDTO(seatToDTO(seat)));
    }


    @PostMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {
        if (!"super_secret".equals(password)) {
            Map<String, String> m = Map.of("error", "The password is wrong!");
            return new ResponseEntity<>(m, HttpStatus.UNAUTHORIZED);
        } else {
            int income = room.getSeats().stream()
                    .filter(s -> s.getToken() != null)
                    .collect(Collectors.summingInt(Seat::getPrice));

            int soldTickets = (int) room.getSeats().stream()
                    .filter(s -> s.getToken() != null)
                    .count();

            int availableSeats = room.getSeats().size() - soldTickets;

            return ResponseEntity.ok(new StatsDTO(income, availableSeats, soldTickets));
        }
    }

    private SeatDTO seatToDTO(Seat seat) {
        return new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

    private TicketDTO seatToTicketDTO(Seat seat) {
        return new TicketDTO(seat.getToken(), seatToDTO(seat));
    }

    private RoomDTO roomToDTO(Room room) {
        List<SeatDTO> seatsDTO = new LinkedList<>();
        for(int i = 0; i < room.getTotalColumns() * room.getTotalRows(); i++)
            seatsDTO.add(seatToDTO(room.getSeat(i)));
        return new RoomDTO(room.getTotalRows(), room.getTotalColumns(), seatsDTO);
    }

}
