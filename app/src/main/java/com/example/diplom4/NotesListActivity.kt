package com.example.diplom4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.*
import androidx.room.Room

import kotlinx.android.synthetic.main.activity_notes_list.*
import java.lang.Exception
import java.util.*

class NotesListActivity : AppCompatActivity() {

    private var simpleAdapterContent: MutableList<Map<String, String>> = ArrayList()
    private var myListSharedPref: SharedPreferences? = null

    private lateinit var listContentAdapter: BaseAdapter
    private lateinit var db: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        db = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java, "database"
        ).allowMainThreadQueries().build()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
//            Toast.makeText(
//                this@NotesListActivity,
//                "Swipe Refresh",
//                Toast.LENGTH_LONG
//            ).show()
//
//            prepareContent(myListSharedPref!!.getString("MyList", ""))
//            listContentAdapter.notifyDataSetChanged()
        }

        val listView = findViewById<ListView>(R.id.list)

        myListSharedPref = getSharedPreferences("MyList", Context.MODE_PRIVATE)

        if (myListSharedPref?.contains("MyList") != true) {
            val myEditor = myListSharedPref!!.edit()
            myEditor.putString("MyList", getString(R.string.large_text))
            myEditor.apply()
            Toast.makeText(this@NotesListActivity, "данные сохранены", Toast.LENGTH_LONG).show()
        }

        val note1 = NoteModel(4, "aaa1", "bbbccc", 0)
        val note2 = NoteModel(5, "aaa2", "bbbddd", 0)
        val note3 = NoteModel(6, "aaa3", "bbbeee", 0)

        try {
            db.noteDao().insert(note1)
            db.noteDao().insert(note2)
            db.noteDao().insert(note3)
        } catch (e: Exception) {
            Toast.makeText(this@NotesListActivity, "some error", Toast.LENGTH_LONG).show()
        }

        prepareContent(db.noteDao().all)
        listContentAdapter = createAdapter()

        listView.adapter = listContentAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            simpleAdapterContent.removeAt(position)
            listContentAdapter.notifyDataSetChanged()

            Toast.makeText(
                this@NotesListActivity,
                "Position $position removed",
                Toast.LENGTH_LONG
            ).show()
        }
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

    companion object {

        // MyList constants
        const val TITLE = "title"
        const val TEXT = "text"
        const val DEADLINE = "deadline"
    }
}
