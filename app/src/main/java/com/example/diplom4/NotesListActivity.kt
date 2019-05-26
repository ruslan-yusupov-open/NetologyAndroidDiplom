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

        prepareData()
        initList(listView)

        floatingAddButton.setOnClickListener { addButtonOnClickListener() }
        ibCustomSettings.setOnClickListener { settingsButtonOnClickListener() }
    }

    private fun initList(listView: ListView) {
        listContentAdapter = createAdapter()
        listView.adapter = listContentAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            listItemOnClickListener(position)
        }
    }

    private fun listItemOnClickListener(position: Int) {
        val intent = Intent(this@NotesListActivity, NoteEditActivity::class.java)
        intent.putExtra("noteId", notes[position].id)
        startActivity(intent)
    }

    private fun addButtonOnClickListener() {
        val intent = Intent(this@NotesListActivity, NoteEditActivity::class.java)
        startActivity(intent)
    }

    private fun settingsButtonOnClickListener() {
        val intent = Intent(this@NotesListActivity, SettingsActivity::class.java)
        startActivity(intent)
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

        prepareData()
        listContentAdapter.notifyDataSetChanged()
    }

    private fun prepareData() {
        notes = db.noteDao().all
        prepareContent(notes)
    }

    companion object {

        // MyList constants
        const val TITLE = "title"
        const val TEXT = "text"
        const val DEADLINE = "deadline"
    }
}
