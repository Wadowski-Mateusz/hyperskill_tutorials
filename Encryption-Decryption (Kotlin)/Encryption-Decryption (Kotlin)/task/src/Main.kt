package encryptdecrypt

fun main() {
    val msg = readln().lowercase()
    val key = readln().toInt()
//    val msg = "welcode to hyperskill"
//    val key = 5

    val encMsg = buildString {
        for (char in msg) {
            if (char in 'a'..'z')
                append( ((char.code - 'a'.code + key) % 26 + 'a'.code).toChar()  )
            else
                append(char)
        }
    }

    println(encMsg)

}