package com.example.diplom4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_notes_list.*
import kotlinx.android.synthetic.main.content_notes_list.*
import java.text.SimpleDateFormat
import java.util.*

class NotesListActivity : AppCompatActivity() {

    private var simpleAdapterContent: MutableList<Map<String, String>> = ArrayList()

    private lateinit var listContentAdapter: BaseAdapter
    private lateinit var db: NoteDatabase

    private lateinit var notes: List<NoteModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        setSupportActionBar(mainToolbar)

        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "database"
        ).allowMainThreadQueries().build()

        prepareData()
        initList(notesListView)

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
        val sAdapter = SimpleAdapter(
            this,
            simpleAdapterContent,
            R.layout.list_item,
            arrayOf(TITLE, TEXT, DEADLINE), intArrayOf(R.id.textViewTitle, R.id.textViewText, R.id.textViewDeadline)
        )

        sAdapter.viewBinder = SimpleAdapter.ViewBinder { view, data, _ ->
            view.visibility = if (data.toString() == "") View.GONE else View.VISIBLE
            (view as TextView).text = data.toString()
            true
        }

        return sAdapter
    }

    private fun prepareContent(notes: List<NoteModel>) {
        simpleAdapterContent.clear()

        for (note in notes) {
            val row = HashMap<String, String>()
            row[TITLE] = note.title
            row[TEXT] = note.text
            row[DEADLINE] =
                if (note.deadline > 0) SimpleDateFormat("yyyy-M-d", Locale.US).format(Date(note.deadline)) else ""

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
