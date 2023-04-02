package ex02

import java.io.File

// write a Kotlin program that finds the longest word in a file.

fun main() {
    val filename = "src/main/kotlin/ex02/words_sequence.txt"
    val file = File(filename)
    if(!file.exists()) {
        println("File doesn't exist")
        return
    }

    var longest: Int = 0
    file.forEachLine {
        if(it.length > longest)
            longest = it.length
    }
    println(longest)
}