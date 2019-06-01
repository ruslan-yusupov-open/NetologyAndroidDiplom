package com.example.diplom4

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class PincodeService(private val context: Context) {
    private var sharedPrefs: SharedPreferences = context.getSharedPreferences("pincode", Context.MODE_PRIVATE)

    fun hasPin() = sharedPrefs.contains("pin")

    fun checkPin(pin: String) = sharedPrefs.getString("pin", "") == pin

    fun setPin(newPin: String) {
        sharedPrefs.edit().putString("pin", newPin).apply()
        Toast.makeText(context, "pincode saved", Toast.LENGTH_LONG).show()
    }

    fun resetPin() {
        sharedPrefs.edit().remove("pin").apply()
        Toast.makeText(context, "pincode reset", Toast.LENGTH_LONG).show()
    }
}