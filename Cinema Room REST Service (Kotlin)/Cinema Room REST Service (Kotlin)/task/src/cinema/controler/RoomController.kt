package cinema.controler

import cinema.entity.Room
import cinema.entity.Seat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController {

    private val totalRows = 9
    private val totalColumns = 9
    private val seats: MutableList<Seat> = mutableListOf()

    init {
        for (row in 1..totalRows)
            for (column in 1..totalColumns)
                seats.add(Seat(row, column))
    }

    @GetMapping("/seats")
    fun getSeats(): Room {
        return Room(totalRows, totalColumns, seats)
    }

}