package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class profile_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // send user to prev screen
        findViewById<Button>(R.id.go_back_button).setOnClickListener {
            val intent = Intent(this@profile_activity, MainActivity::class.java)
            startActivity(intent)
        }

        // send user to logout confirmation screen
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            val intent = Intent(this@profile_activity, logoutConfirmationActivity::class.java)
            startActivity(intent)
        }

        // send user to edit profile screen
        findViewById<Button>(R.id.edit_profile).setOnClickListener {
            val intent = Intent(this@profile_activity, editProfileActivity::class.java)
            startActivity(intent)
        }

    }
}