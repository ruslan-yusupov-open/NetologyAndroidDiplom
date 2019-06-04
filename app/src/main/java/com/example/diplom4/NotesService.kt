package com.example.diplom4

import android.content.Context
import androidx.room.Room

class NotesService(context: Context) {

    private var db: NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java, "database"
    ).allowMainThreadQueries().build()

    fun getSortedNotesList() =
        db.noteDao().withDeadlineSortedByDeadlineAsc + db.noteDao().withoutDeadlineSortedByUpdated

    fun getNoteById(id: Long) = db.noteDao().getById(id)

    fun deleteNote(note: NoteModel) = db.noteDao().delete(note)

    fun insertNote(note: NoteModel) = db.noteDao().insert(note)

    fun updateNote(note: NoteModel) = db.noteDao().update(note)
}
