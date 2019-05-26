package com.example.diplom4

import java.util.*

data class NoteModel(val title: String, val text: String, val deadline: Date?) {
    fun iptext(): String {
        return text
    }
}
