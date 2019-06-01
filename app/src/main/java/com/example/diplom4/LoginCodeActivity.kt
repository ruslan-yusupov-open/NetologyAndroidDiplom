package com.example.diplom4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login_code.*

class LoginCodeActivity : AppCompatActivity() {

    private var counter = 0
    private var pinString = ""

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
        buttonDel.setOnClickListener { onClickDel() }

        if (!PincodeService(this).hasPin()) {
            Toast.makeText(this, "please set pincode", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onClick(it: View?) {
        if (counter < 4) {
            pinString += when (it) {
                digit0Button -> "0"
                digit1Button -> "1"
                digit2Button -> "2"
                digit3Button -> "3"
                digit4Button -> "4"
                digit5Button -> "5"
                digit6Button -> "6"
                digit7Button -> "7"
                digit8Button -> "8"
                digit9Button -> "9"
                else -> ""
            }

            when (++counter) {
                1 -> {
                    black1.visibility = View.GONE
                    yellow1.visibility = View.VISIBLE
                }
                2 -> {
                    black2.visibility = View.GONE
                    yellow2.visibility = View.VISIBLE
                }
                3 -> {
                    black3.visibility = View.GONE
                    yellow3.visibility = View.VISIBLE
                }
                4 -> {
                    black4.visibility = View.GONE
                    yellow4.visibility = View.VISIBLE

                    checkPinCode()
                }
            }
        }
    }

    private fun checkPinCode() {
        if (PincodeService(this).checkPin(pinString)) {
            Toast.makeText(this, "pin code correct", Toast.LENGTH_LONG).show()
            val intent = Intent(this, NotesListActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "pin code wrong", Toast.LENGTH_LONG).show()
            while (counter > 0) onClickDel()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onClickDel() {
        if (counter > 0) {
            when (counter--) {
                4 -> {
                    black4.visibility = View.VISIBLE
                    yellow4.visibility = View.GONE
                }
                3 -> {
                    black3.visibility = View.VISIBLE
                    yellow3.visibility = View.GONE
                }
                2 -> {
                    black2.visibility = View.VISIBLE
                    yellow2.visibility = View.GONE
                }
                1 -> {
                    black1.visibility = View.VISIBLE
                    yellow1.visibility = View.GONE
                }
            }

            pinString = pinString.substring(0, pinString.length - 1)
        }

    }
}
