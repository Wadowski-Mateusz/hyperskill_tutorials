package cinema.Models;

import java.util.Objects;

public final class Seat {
    private final int row;
    private final int column;
    private final int price;
    private boolean isTaken;

    public Seat(int row, int column, int price, boolean isTaken) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isTaken = isTaken;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
