package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {
    var mode: String = "dec"
    var data: String = ""
    var key: Int = 0
    var inputFilename: String = ""
    var outputFilename: String = ""
    var algorithm: String = "shift"

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
        if (args.indexOf("-alg") != -1)
            algorithm = args[args.indexOf("-alg") + 1]
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
            "enc" -> encryption(inputString, key, algorithm)
            "dec" -> decryption(inputString, key, algorithm)
            else -> {println("No such a command!"); return}
        }

    try {
        if (outputFilename.isEmpty()) {
            println(message)
        }
        else
            writeToFile(outputFilename, message)
    } catch (e: Exception) {
        println("Error")
        return
    }

}

fun encryption(message: String, key: Int, alg: String): String =
    if (alg == "unicode")
        buildString { message.map { append((it.code + key).toChar()) } }
    else // if(alg == "shift")
        buildString {
            message
                .map {
                    when (it) {
                        in 'a'..'z' -> append( ((it.code - 'a'.code + key) % 26 + 'a'.code).toChar()  )
                        in 'A'..'Z' -> append( ((it.code - 'A'.code + key) % 26 + 'A'.code).toChar()  )
                        else -> append(it)
                    }
                }
        }

fun decryption(ciphertext: String, key: Int, alg: String): String =
    if (alg == "unicode")
        buildString { ciphertext.map { append((it.code - key).toChar()) } }
    else // if(alg == "shift")
        buildString {
            ciphertext
                .map {
                    when (it.code) {
                        in 'a'.code + key..'z'.code + key -> append( (it.code - key).toChar()  )
                        in 'a'.code..'z'.code -> append( (it.code - key + 26).toChar()  )
                        in 'A'.code + key..'Z'.code + key -> append( (it.code - key).toChar()  )
                        in 'A'.code..'Z'.code -> append( (it.code - key + 26).toChar()  )
                        else -> append(it)
                    }
                }
        }



fun readFromFile(inputFilename: String): String = File(inputFilename).readText()

fun writeToFile(outputFilename: String, message: String) = File(outputFilename).writeText(message)


