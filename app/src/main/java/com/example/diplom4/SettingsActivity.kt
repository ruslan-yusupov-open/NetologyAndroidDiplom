package com.example.diplom4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        customCancelImageButton.setOnClickListener { onBack() }
        saveButton.setOnClickListener { onSave() }
    }

    private fun onBack() {
        finish()
    }

    private fun onSave() {
        val pinString = pinEditText.text.toString()

        if (pinString.length != 4) {
            Toast.makeText(this, "Pincode should be 4 digits", Toast.LENGTH_LONG).show()
            return
        }

        PincodeService(this).setPin(pinString)

        finish()
    }
}
