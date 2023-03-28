var purchasedTickets: Int = 0
var currentIncome: Int = 0
var totalIncome: Int = 0
var totalSeats: Int = 0


fun main() {
    val room: MutableList<MutableList<String>> = generateRoom()

    while(true) {
        showMenu()
        when (readln().toInt()) {
            1 -> showRoom(room)
            2 -> buySeat(room)
            3 -> showStatistics()
            0 -> break
        }
        println("")
    }
}

fun showMenu() {
    println("""
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    
    """.trimIndent())
}

fun showStatistics() {
    val percentage: String = "%.2f".format(purchasedTickets.toFloat() / totalSeats.toFloat() * 100) + "%"
    println("""
        Number of purchased tickets: $purchasedTickets
        Percentage: $percentage
        Current income: $$currentIncome
        Total income: $$totalIncome

    """.trimIndent())
}

fun buySeat(room: MutableList<MutableList<String>>) {
    while (true) {
        try {
            println("Enter a row number:")
            val row = readln().toInt()

            println("Enter a seat number in that row:")
            val seat = readln().toInt()

            if (room[row][seat] == "B")
                throw IllegalArgumentException() // when seat is taken

            val price = calculatePrice(room.size - 1, row)  // -1 for labels
            println("Ticket price: $$price")
            room[row][seat] = "B"
            currentIncome += price
            purchasedTickets++

            break
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!\n")
        } catch (e: IllegalArgumentException) {
            println("That ticket has already been purchased!\n")
        }
    }
}

fun calculatePrice(rows: Int, selectedRow: Int): Int {
    if (totalSeats <= 60) {
        return 10
    } else {
        return if (selectedRow > rows / 2) 8 else 10
    }
}

fun showRoom(room: MutableList<MutableList<String>>) {
    println("Cinema:")
    for (i in 0 until room.size) {
        println(room[i].joinToString(" "))
    }
}

fun calculateTotalIncome(rows: Int, seats: Int) {
    for (rowNumber in 1..rows) {
        totalIncome += calculatePrice(rows, rowNumber) * seats
    }
}

fun generateRoom(): MutableList<MutableList<String>> {

    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    totalSeats = rows * seats
    calculateTotalIncome(rows, seats)

    val room = mutableListOf<MutableList<String>>()
    val labelRow: MutableList<String> = mutableListOf<String>(" ")

    for (i in 1..seats) {
        labelRow.add(i.toString())
    }
    room.add(labelRow)

    for (i in 1..rows){
        val seatsRow = mutableListOf<String>()
        seatsRow.add("$i")
        for (j in 0 until seats)
            seatsRow.add("S")
        room.add(seatsRow)
    }
    println("")
    return room
}