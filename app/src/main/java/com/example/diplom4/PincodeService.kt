package com.example.diplom4

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import java.security.MessageDigest

class PincodeService(private val context: Context) {
    private var sharedPrefs: SharedPreferences = context.getSharedPreferences("pincode", Context.MODE_PRIVATE)

    fun hasPin() = sharedPrefs.contains("hashedPin") && sharedPrefs.contains("salt")

    fun checkPin(pin: String): Boolean {
        val salt = sharedPrefs.getString("salt", "")
        val hashedString = hash(pin + salt)
        return sharedPrefs.getString("hashedPin", "") == hashedString
    }

    fun setPin(newPin: String) {
        val salt = kotlin.random.Random.nextInt().toString()
        val hashedString = hash(newPin + salt)

        sharedPrefs.edit().putString("hashedPin", hashedString).apply()
        sharedPrefs.edit().putString("salt", salt).apply()
        Toast.makeText(context, "pincode saved", Toast.LENGTH_LONG).show()
    }

    fun resetPin() {
        sharedPrefs.edit().remove("hashedPin").apply()
        sharedPrefs.edit().remove("salt").apply()
        Toast.makeText(context, "pincode reset", Toast.LENGTH_LONG).show()
    }

    private fun hash(strToHash: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(strToHash.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }
}
