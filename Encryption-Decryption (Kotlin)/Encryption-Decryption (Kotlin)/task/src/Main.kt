package encryptdecrypt

fun main(args: Array<String>) {
    var mode: String = "dec"
    var data: String = ""
    var key: Int = 0

    if (args.isNotEmpty()) {
        if (args.indexOf("-mode") != -1)
            mode = args[args.indexOf("-mode") + 1]
        if (args.indexOf("-key") != -1)
            key = args[args.indexOf("-key") + 1].toInt()
        if (args.indexOf("-data") != -1)
            data = args[args.indexOf("-data") + 1]
    }

    val output: String = when (mode) {
        "enc" -> encryption(data, key)
        "dec" -> decryption(data, key)
        else -> "No such a command!"
    }
    println(output)
}

fun encryption(message: String, key: Int): String =
    buildString { message.map { append((it.code + key).toChar()) } }

fun decryption(ciphertext: String, key: Int): String =
    buildString { ciphertext.map { append((it.code - key).toChar()) } }

//  // encryption
//    val encMsg = buildString {
//        for (char in msg) {
//            if (char in 'a'..'z')
//                append( ((char.code - 'a'.code + key) % 26 + 'a'.code).toChar()  )
//            else
//                append((char.code).toChar())
//        }
//    }
