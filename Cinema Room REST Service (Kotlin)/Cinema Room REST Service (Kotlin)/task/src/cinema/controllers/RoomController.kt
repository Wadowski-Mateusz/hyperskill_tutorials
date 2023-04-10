package cinema.controllers

import cinema.Room
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController {

    @GetMapping("/seats")
    fun getRoom(): Room {
        return Room(9, 9)
    }

}