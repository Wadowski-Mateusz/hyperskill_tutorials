package cinema.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Room {

    private final int totalRows;
    private final int totalColumns;
    private final List<Seat> seats = new ArrayList<>();

    public Room() {
        totalColumns = 0;
        totalRows = 0;
    }

    public Room(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        for (int row = 1; row <= totalRows; row++) {
            int price = (row > 4 ? 8 : 10);
            for (int column = 1; column <= totalColumns; column++)
                seats.add(new Seat(row, column, price));
//                seats.add(new Seat(row, column, price, false));
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Seat getSeat(int row, int column) {
        return seats.get(row * totalColumns + column);
    }

    // index = (seat.row - 1) * this.totalColumns + seat.column - 1
    public Seat getSeat(int index) {
        return seats.get(index);
    }

    public Boolean isSeatTaken(int row, int column) {
        return seats.get(row*totalColumns + column).getToken() != null;
//        return seats.get(row*totalColumns + column).isTaken();
    }

    public void takeSeat(int row, int column) {
        seats.get(row * totalColumns + column).setToken(UUID.randomUUID());
//        seats.get(row * totalColumns + column).setTaken(true);
    }

    public void freeSeat(int row, int column) {
        seats.get(row * totalColumns + column).setToken(null);
//        seats.get(row * totalColumns + column).setTaken(true);
    }


}
