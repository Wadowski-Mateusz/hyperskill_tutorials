package encryptdecrypt

fun main() {
    val choice: String = readln()
    val message: String = readln()
    val key: Int = readln().toInt()
    val output: String = when (choice) {
        "enc" -> encryption(message, key)
        "dec" -> decryption(message, key)
        else -> "No such a command!"
    }
    println(output)
}

fun encryption(message: String, key: Int): String =
    buildString { message.map { append((it.code + key).toChar()) } }
//    val encMsg = buildString {
//        for (char in msg) {
//            if (char in 'a'..'z')
//                append( ((char.code - 'a'.code + key) % 26 + 'a'.code).toChar()  )
//            else
//                append((char.code).toChar())
//        }
//    }

fun decryption(ciphertext: String, key: Int): String =
    buildString { ciphertext.map { append((it.code - key).toChar()) } }