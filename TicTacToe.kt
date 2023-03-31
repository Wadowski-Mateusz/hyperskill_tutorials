package tictactoe

const val PLAYER1: Char = 'X'
const val PLAYER2: Char = 'O'
const val BLANK: Char = ' '

fun main() {
    var board: String = BLANK.toString().repeat(9)
    showBoard(board)
    var point: String
    var result: String
    do {
        do {
            point = readln()
        } while (!validateInput(board, point)) 
        board = makeMove(board, point)
        showBoard(board)
        result = analyzePosition(board)
    } while (!result.contains("wins") && !result.contains("Draw") )
    print(result)
    

}

fun makeMove(board: String, point: String): String {
    val (x: Int, y: Int) = point.split(" ").map { it.toInt() }
    val index = x * 3 + y - 4 // substract 4; user input position from 1..3, not from 0..2
    return board.substring(0, index) + 'X' + board.substring(index + 1, board.length)
}


fun validateInput(board: String, point: String): Boolean{
    try {
        val (x: Int, y: Int) = point.split(" ").map { it.toInt() }
        // substract 4; user input position from 1..3, not from 0..2
        if (board[x * 3 + y - 4] != BLANK) {
            println("This cell is occupied! Choose another one!")
            return false
        }
    } catch (e: NumberFormatException){
        println("You should enter numbers!")
        return false
    } catch (e: StringIndexOutOfBoundsException) {
        println("Coordinates should be from 1 to 3!")
        return false
    }
    return true
}


fun showBoard(board: String) {
    val HORIZONTAL_BOUNDARY = "---------"
    println(HORIZONTAL_BOUNDARY)
    for (i in 0..2)
        println("| ${board[i * 3]} ${board[i * 3 + 1]} ${board[i * 3 + 2]} |")
    println(HORIZONTAL_BOUNDARY)
}

fun analyzePosition(board: String): String {

    val xCounter = board.count { it == 'X' }
    val oCounter = board.count { it == 'O' }

    // if (Math.abs(xCounter - oCounter) > 1)
    //     return "Impossible"

    // if (winCheck(board, 'O') && winCheck(board, 'X')) return "Impossible"
    
    // Did anyone win?
    if (winCheck(board, 'O')) return "O wins"
    if (winCheck(board, 'X')) return "X wins"

    
    // is it a draw or are they plaing?
    return if(xCounter + oCounter == 9) "Draw" else "Game not finished"

}

fun winCheck(board: String, player: Char): Boolean {
    // well, that is more readable than loops I guess    

    // rows
    if (board.substring(0, 3) == "$player$player$player" 
        || board.substring(3, 6) == "$player$player$player"
        || board.substring(6, 9) == "$player$player$player")
    return true

    // columns
    if (board.slice(0..8 step 3) == "$player$player$player" 
        || board.slice(1..8 step 3) == "$player$player$player"
        || board.slice(2..8 step 3) == "$player$player$player")
    return true

    // cross /
    if (board[0] == player && board[4] == player && board[8] == player)
        return true
    // cross \
    if (board[2] == player && board[4] == player && board[6] == player)
        return true

    return false  
}
