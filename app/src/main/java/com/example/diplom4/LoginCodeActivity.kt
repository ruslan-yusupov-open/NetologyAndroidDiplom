package com.example.diplom4

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_code.*

class LoginCodeActivity : AppCompatActivity() {

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_code)

        digit0Button.setOnClickListener(this::onClick)
        digit1Button.setOnClickListener(this::onClick)
        digit2Button.setOnClickListener(this::onClick)
        digit3Button.setOnClickListener(this::onClick)
        digit4Button.setOnClickListener(this::onClick)
        digit5Button.setOnClickListener(this::onClick)
        digit6Button.setOnClickListener(this::onClick)
        digit7Button.setOnClickListener(this::onClick)
        digit8Button.setOnClickListener(this::onClick)
        digit9Button.setOnClickListener(this::onClick)
    }

    private fun onClick(it: View?) {
        when (counter++) {
            0 -> {
                black1.visibility = View.GONE
                yellow1.visibility = View.VISIBLE
            }
            1 -> {
                black2.visibility = View.GONE
                yellow2.visibility = View.VISIBLE
            }
            2 -> {
                black3.visibility = View.GONE
                yellow3.visibility = View.VISIBLE
            }
            3 -> {
                black4.visibility = View.GONE
                yellow4.visibility = View.VISIBLE

                val intent = Intent(this, NotesListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
