package mateusz.introduction.demo.Controllers

import mateusz.introduction.demo.Task
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController {
    @GetMapping("/test")
    fun returnOne(): Int {
        return 1
    }

    val taskList = listOf(
        Task(1, "task1", "A first test task", false),
        Task(2, "task2", "A second test task", true)
    )

    @GetMapping("/tasks")
    fun getTasks(): List<Task> {
        return taskList
    }

    @GetMapping("/tasks/{id}")
    fun getTasks(@PathVariable id: Int): ResponseEntity<Task?>? {
        return ResponseEntity(taskList[id - 1], HttpStatus.ACCEPTED)
    }
}
