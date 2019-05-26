package com.example.diplom4

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_notes_list.*
import java.util.*

class NotesListActivity : AppCompatActivity() {

    private var simpleAdapterContent: MutableList<Map<String, String>> = ArrayList()

    private lateinit var listContentAdapter: BaseAdapter
    private lateinit var db: NoteDatabase

    private lateinit var notes: List<NoteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "database"
        ).allowMainThreadQueries().build()


        val listView = findViewById<ListView>(R.id.list)

        val note1 = NoteModel(0, "aaa1", "bbbccc", 0)

        try {
            db.noteDao().insert(note1)
        } catch (e: Exception) {
            Toast.makeText(this@NotesListActivity, "some error", Toast.LENGTH_LONG).show()
        }

        notes = db.noteDao().all
        prepareContent(notes)

        listContentAdapter = createAdapter()
        listView.adapter = listContentAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            listItemOnClickListener(position)
        }

        fab.setOnClickListener { addButtonOnClickListener() }
    }

    private fun listItemOnClickListener(position: Int) {
        val intent = Intent(this@NotesListActivity, NoteEditActivity::class.java)
        intent.putExtra("noteId", notes[position].id)
        startActivity(intent)

        /*
        try {
            simpleAdapterContent.removeAt(position)
            db.noteDao().delete(notes[position])
            notes = db.noteDao().all

            Toast.makeText(this@NotesListActivity, "Position $position removed", Toast.LENGTH_LONG).show()

            listContentAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(
                this@NotesListActivity,
                "some error $notes $position", Toast.LENGTH_LONG
            ).show()
        }*/
    }

    private fun addButtonOnClickListener() {
        val intent = Intent(this@NotesListActivity, NoteEditActivity::class.java)
        startActivity(intent)

        /*
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
//
//        val newNote = NoteModel(0, "aaa1", Date().toString(), 0)
//
//        try {
//            db.noteDao().insert(newNote)
//            Toast.makeText(this@NotesListActivity, "inserted", Toast.LENGTH_LONG).show()
//
//            notes = db.noteDao().all
//            prepareContent(notes)
//            listContentAdapter.notifyDataSetChanged()
//        } catch (e: Exception) {
//            Toast.makeText(this@NotesListActivity, "some error", Toast.LENGTH_LONG).show()
//        }
        */
    }

    private fun createAdapter(): BaseAdapter {
        return SimpleAdapter(
            this,
            simpleAdapterContent,
            R.layout.list_item,
            arrayOf(TITLE, TEXT, DEADLINE), intArrayOf(R.id.textViewTitle, R.id.textViewText, R.id.textViewDeadline)
        )
    }

    private fun prepareContent(notes: List<NoteModel>) {
        simpleAdapterContent.clear()

        for (note in notes) {
            val row = HashMap<String, String>()
            row[TITLE] = note.title
            row[TEXT] = note.text
            row[DEADLINE] = note.deadline.toString()

            simpleAdapterContent.add(row)
        }
    }

    override fun onResume() {
        super.onResume()

        notes = db.noteDao().all
        prepareContent(notes)
        listContentAdapter.notifyDataSetChanged()
    }

    companion object {

        // MyList constants
        const val TITLE = "title"
        const val TEXT = "text"
        const val DEADLINE = "deadline"
    }
}
