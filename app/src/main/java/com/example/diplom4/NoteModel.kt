package com.example.diplom4

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var title: String,

    var text: String,

    var deadline: Long,

    var updated: Long
) {

    @Ignore
    constructor() : this(0, "", "", 0, 0)
}
