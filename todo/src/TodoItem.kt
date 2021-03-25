package com.abhi

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import java.time.LocalDate

data class TodoItem(
    val id:Int,
    val titile: String,
    val details: String,
    val assignedTo: String,
    @JsonSerialize(using = ToStringSerializer::class)
    //@JsonDeserialize(using = LocalDatetimeDeserializer::class)
    var dueDate: LocalDate,
    val importance: Importance
)

enum class Importance {
    LOW, MEDIUM, HIGH
}