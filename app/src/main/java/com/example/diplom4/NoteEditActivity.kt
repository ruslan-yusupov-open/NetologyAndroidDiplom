package com.example.diplom4

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_note_edit.*
import java.util.*


class NoteEditActivity : AppCompatActivity() {

    var isNew = true
    private lateinit var noteForEdit: NoteModel
    private lateinit var db: NoteDatabase

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        initNodeForEdit()
        initTooltipButtons()

        noteTitleEditText.setText(noteForEdit.title)
        noteTextEditText.setText(noteForEdit.text)

        ibOpenCalendar.setOnClickListener {
            val todayCalendar = Calendar.getInstance()
            lateinit var datePickerDialog: DatePickerDialog
            datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    deadlineEditText.setText("$dayOfMonth.$monthOfYear.$year")
                    datePickerDialog.dismiss()
                },
                todayCalendar.get(Calendar.YEAR),
                todayCalendar.get(Calendar.MONTH),
                todayCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        serveTextView.text = noteForEdit.toString()
    }

    private fun initNodeForEdit() {
        val noteId = intent.getIntExtra("noteId", 0)

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
    }

    private fun initTooltipButtons() {
        customCancelImageButton.setOnClickListener { finish() }

        if (isNew) {
            activityTitleTextView.text = getString(R.string.new_note)
            customDeleteImageButton.visibility = View.GONE
        } else {
            customDeleteImageButton.setOnClickListener {
                db.noteDao().delete(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note removed", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        customSaveImageButton.setOnClickListener {
            noteForEdit.title = noteTitleEditText.text.toString()
            noteForEdit.text = noteTextEditText.text.toString()

            if (isNew) {
                db.noteDao().insert(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note added", Toast.LENGTH_LONG).show()
            } else {
                db.noteDao().update(noteForEdit)
                Toast.makeText(this@NoteEditActivity, "Note updated", Toast.LENGTH_LONG).show()
            }

            finish()
        }
    }
}
