package cinema

class Room(val total_rows: Int, val total_columns: Int) {
//    val total_rows: Int = 3
//    val total_columns: Int = 2

    val available_seats: List<Seat> = buildList {
        for (row in 1..total_rows)
            for (column in 1..total_columns)
                add(Seat(row, column))
    }

}