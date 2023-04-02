package ex01

import java.io.File

/*
* There is a file that contains a sequence of words and numbers.
* Write a Kotlin program that counts only numbers in this file.
* */

fun main() {
    val filename = "src/main/kotlin/ex01/words_with_numbers.txt"
    val file = File(filename)
    if(!file.exists()) {
        println("File doesn't exist")
        println(System.getProperty("user.dir"))
        return
    }
    var counter = 0
    file.forEachLine {s ->
        if(s.all { c -> c.isDigit() })
            counter++
    }
    println(counter)
}

