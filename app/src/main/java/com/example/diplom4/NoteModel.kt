package com.example.diplom4

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String,

    val text: String,

    val deadline: Long
) {

    @Ignore
    constructor() : this(0, "", "", 0)
}
