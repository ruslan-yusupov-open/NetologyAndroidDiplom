package com.example.diplom4

import androidx.room.*

@Dao
interface NoteModelDao {

    @get:Query("SELECT * FROM NoteModel")
    val all: List<NoteModel>

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    fun getById(id: Long): NoteModel

    @Insert
    fun insert(note: NoteModel)

    @Update
    fun update(note: NoteModel)

    @Delete
    fun delete(note: NoteModel)

}
