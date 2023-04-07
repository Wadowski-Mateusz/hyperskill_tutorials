package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {
    var mode: String = "dec"
    var data: String = ""
    var key: Int = 0
    var inputFilename: String = ""
    var outputFilename: String = ""

    if (args.isNotEmpty()) {
        if (args.indexOf("-mode") != -1)
            mode = args[args.indexOf("-mode") + 1]
        if (args.indexOf("-key") != -1)
            key = args[args.indexOf("-key") + 1].toInt()
        if (args.indexOf("-data") != -1)
            data = args[args.indexOf("-data") + 1]
        if (args.indexOf("-in") != -1)
            inputFilename = args[args.indexOf("-in") + 1]
        if (args.indexOf("-out") != -1)
            outputFilename = args[args.indexOf("-out") + 1]
    }

    val inputString: String =
        try {
            if (data.isNotEmpty())
                data
            else if (inputFilename.isNotEmpty())
                readFromFile(inputFilename)
            else
                ""
        } catch (e: Exception) {
            println("Error")
            return
        }

    val message: String =
        when (mode) {
            "enc" -> encryption(inputString, key)
            "dec" -> decryption(inputString, key)
            else -> {println("No such a command!"); return}
        }

    try {
        if (outputFilename.isEmpty())
            println(message)
        else
            writeToFile(outputFilename, message)
    } catch (e: Exception) {
        println("Error")
        return
    }

}

fun encryption(message: String, key: Int): String =
    buildString { message.map { append((it.code + key).toChar()) } }

fun decryption(ciphertext: String, key: Int): String =
    buildString { ciphertext.map { append((it.code - key).toChar()) } }

fun readFromFile(inputFilename: String): String = File(inputFilename).readText()

fun writeToFile(outputFilename: String, message: String) = File(outputFilename).writeText(message)


//  // encryption
//    val encMsg = buildString {
//        for (char in msg) {
//            if (char in 'a'..'z')
//                append( ((char.code - 'a'.code + key) % 26 + 'a'.code).toChar()  )
//            else
//                append((char.code).toChar())
//        }
//    }
