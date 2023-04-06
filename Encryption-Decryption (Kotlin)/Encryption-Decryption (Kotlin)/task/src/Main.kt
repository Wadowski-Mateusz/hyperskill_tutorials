package encryptdecrypt

fun main() {
    val msg = "we found a treasure!".lowercase()
//    val msg = "abcdefghijklmnopqrstuvwxyz!".lowercase()
    val encMsg = buildString {
        for (char in msg) {
            if (char in 'a'..'z')
                append( ('z'.code - char.code + 'a'.code).toChar() )
            else
                append(char)
        }
    }

    println(encMsg)
}