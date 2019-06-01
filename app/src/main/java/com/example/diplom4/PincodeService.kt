package com.example.diplom4

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class PincodeService(private val context: Context) {
    private var sharedPrefs: SharedPreferences = context.getSharedPreferences("pincode", Context.MODE_PRIVATE)

    fun checkPin(pin: String): Boolean? {
        return if (!sharedPrefs.contains("pin")) null else sharedPrefs.getString("pin", "") == pin
    }

    fun setPin(newPin: String) {
        val myEditor = sharedPrefs.edit()
        myEditor.putString("pin", newPin)
        myEditor.apply()
        Toast.makeText(context, "pincode saved", Toast.LENGTH_LONG).show()
    }
}