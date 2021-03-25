package com.abhi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.time.LocalDate

fun Routing.todoApi() {
    route("/api")
    {
        header("Accept", "application/json")
        {
            get("/todos")
            {
                call.respond(todos)
            }
        }
        get("/todos/{id}")
        {
            val id: String = call.parameters["id"]!!
            try {
                val todo: TodoItem = todos[id.toInt()]
                call.respond(todo)
            } catch (e: Throwable) {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post("/todos")
        {
            val todo = call.receive<TodoItem>()
            val newTodo =
                TodoItem(todos.size + 1, todo.titile, todo.details, todo.assignedTo, todo.dueDate, todo.importance)
            todos = todos + newTodo
            call.respond(HttpStatusCode.Created, todos)
        }

        put("todos/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }
            val foundItem = todos.getOrNull(id.toInt())
            if (foundItem == null) {
                call.respond(HttpStatusCode.NotFound)
            }
            val todo = call.receive<TodoItem>()
            todos = todos.filter { it.id != todo.id }
            todos = todos + todo
            call.respond(HttpStatusCode.NoContent)
        }

        delete("todos/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val foundItem = todos.getOrNull(id.toInt())
            if (foundItem == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            todos = todos.filter { it.id != id.toInt() }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}

val todo1 = TodoItem(
    1,
    "Add RestAPI Data Access",
    "Add database support",
    "Me",
    LocalDate.of(2021, 12, 24),
    Importance.HIGH
)

val todo2 = TodoItem(
    2,
    "Add RestAPI Data Access",
    "Add database support",
    "Me",
    LocalDate.of(2021, 12, 25),
    Importance.MEDIUM
)

val todo3 = TodoItem(
    3,
    "Add RestAPI Data Access Low",
    "Add database support Low",
    "Me",
    LocalDate.of(2021, 12, 26),
    Importance.LOW
)

var todos = listOf(todo1, todo2, todo3)