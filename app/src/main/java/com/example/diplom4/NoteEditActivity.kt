package com.example.diplom4

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

class NoteEditActivity : AppCompatActivity() {

    var isNew = true
    private lateinit var noteForEdit: NoteModel
    private lateinit var db: NoteDatabase

    private lateinit var titleEditText: EditText
    private lateinit var textEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        val noteId = intent.getIntExtra("noteId", 0)

        titleEditText = findViewById(R.id.inputNoteTitle)
        textEditText = findViewById(R.id.inputNoteText)

        findViewById<ImageButton>(R.id.ibCustomCancel).setOnClickListener { finish() }

        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "database"
        ).allowMainThreadQueries().build()


        if (noteId > 0) {
            isNew = false
            noteForEdit = db.noteDao().getById(noteId.toLong())
        } else {
            noteForEdit = NoteModel()
        }

        val deleteButton = findViewById<ImageButton>(R.id.ibCustomDelete)

        if (isNew) {
            findViewById<TextView>(R.id.activity_title).text = getString(R.string.new_note)
            deleteButton.visibility = View.GONE
        } else {
            deleteButton.setOnClickListener {
                db.noteDao().delete(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note removed", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        findViewById<ImageButton>(R.id.ibCustomSave).setOnClickListener {
            noteForEdit.title = titleEditText.text.toString()
            noteForEdit.text = textEditText.text.toString()

            if (isNew) {
                db.noteDao().insert(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note added", Toast.LENGTH_LONG).show()
            } else {
                db.noteDao().update(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note updated", Toast.LENGTH_LONG).show()
            }

            finish()
        }

        titleEditText.setText(noteForEdit.title)
        textEditText.setText(noteForEdit.text)

        findViewById<TextView>(R.id.serveText).text = noteForEdit.toString()
    }
}
