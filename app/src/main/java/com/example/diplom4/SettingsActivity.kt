package com.example.diplom4

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        if (!PincodeService(this).hasPin())
            customCancelImageButton.visibility = View.GONE
        else
            customCancelImageButton.setOnClickListener { onBack() }

        if (!PincodeService(this).hasPin())
            resetButton.visibility = View.GONE
        else
            resetButton.setOnClickListener { resetPincode() }

        saveButton.setOnClickListener { onSave() }

    }

    private fun onBack() = finish()

    private fun onSave() {
        val pinString = pinEditText.text.toString()

        if (pinString.length != 4) {
            Toast.makeText(this, getString(R.string.pincode_should_be_4_digits), Toast.LENGTH_LONG).show()
            return
        }

        PincodeService(this).setPin(pinString)

        finish()
    }

    private fun resetPincode() {
        PincodeService(this).resetPin()
        finish()
    }
}
