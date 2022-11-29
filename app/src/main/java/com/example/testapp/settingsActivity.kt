package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class settingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // send user to logout confirmation screen
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            val intent = Intent(this@settingsActivity, logoutConfirmationActivity::class.java)
            startActivity(intent)
        }

        // send user to delete account confirmation screen
        findViewById<Button>(R.id.delete_account_button).setOnClickListener {
            val intent = Intent(this@settingsActivity, deleteAccountActivity::class.java)
            startActivity(intent)
        }

        // send user to previous screen
        findViewById<Button>(R.id.go_back_button).setOnClickListener {
            val intent = Intent(this@settingsActivity, MainActivity::class.java)
            startActivity(intent)
        }




    }
}