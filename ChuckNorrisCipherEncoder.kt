package chucknorris

fun main() {
    while(true) {
        println("Please input operation (encode/decode/exit):")
        var choice: String = readln()
        when(choice) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> break
            else -> println("There is no '$choice' operation")
        }
        println("")
    }
    println("Bye!")
}

fun encode() {
    println("Input string:")
    val input: String = readln().trim()
    val result = binarytoUnary(decimalToBinary(input))
    println("Encoded string:")
    println(result)
}

fun validate(s: String): Boolean {
    val splitted: List<String> = s.split(" ")
    
    if (splitted.size % 2 != 0) {
    	// println("1 - invalid number of string parts")
    	return false
    }
    
    if (!splitted.all { it.all { it == '0' } }) {
    	// println("2 - illegal chars")
    	return false
    }

	for(i in 0..splitted.lastIndex step 2)
    	if (splitted[i].length > 2) {
        	// println("3 - wrong lenght of 0/1 code")
        	return false
        }
        
    var size = 0    
    for(i in 1..splitted.lastIndex step 2)
    	size += splitted[i].length
    
    if(size % 7 != 0) {
        // println("4 - number of bits is not multiple of 7")
        return false
    }
 	return true   
}


fun decode() {
    println("Input encoded string:")
    val input: String = readln().trim()
    if(!validate(input)) {
        println("Encoded string is not valid.")
        return
    }
    val binary: String = unaryToBinary(input)
	val asciiString: String = binaryToString(binary)
    println("Decoded string:")
    println("$asciiString")
}

fun unaryToBinary(unaryForm: String): String {
    val splitedUnary = unaryForm.trim().split(" ")
    var result = ""
    for (i in 0..splitedUnary.lastIndex step 2) {
        if (splitedUnary[i] == "0") {
        	result += "1".repeat(splitedUnary[i + 1].length)
        } else {
        	result += "0".repeat(splitedUnary[i + 1].length)
        }
    }
    return result
}

fun binaryToString(binaryForm: String, _step: Int = 7): String {
    var result = ""
    for(i in 0..binaryForm.lastIndex step _step) {
    	val code: Int = Integer.parseInt(binaryForm.substring(i, i + _step), 2)
        result += code.toChar()
    }
    return result
}
// or by chat gpt
// fun binaryToString(binaryForm: String, step: Int = 7): String {
//     return binaryForm.chunked(step)
//         .map { Integer.parseInt(it, 2) }
//         .map { it.toChar() }
//         .joinToString("")
// }

fun decimalToBinary(input: String): String {
    var result: String = ""
    for (c in input) {
        val decimalChar = Integer.toBinaryString(c.code)
        val binaryChar = String.format("%7s", decimalChar).replace(" ", "0")
        result += binaryChar
    }
    return result
}

fun binarytoUnary(input: String): String {
    var result = ""
    var counter = 1
    for (i in 0..input.lastIndex) {
        try {
            if(input[i] == input[i + 1]) {
                counter++
            } else {
                result += (if (input[i] == '1') "0 " else "00 ") + "0".repeat(counter) + " "
                counter = 1
            }
        } catch (e: StringIndexOutOfBoundsException) {
            result += (if (input[i] == '1') "0 " else "00 ") + "0".repeat(counter)
        }
    }
    return result
}
