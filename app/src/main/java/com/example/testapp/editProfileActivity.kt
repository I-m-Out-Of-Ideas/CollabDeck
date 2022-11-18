package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class editProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // take picture

        // send user to camera library

        // accept changes

        // cancel changes, return user to profile screen
        findViewById<Button>(R.id.cancel_button).setOnClickListener {
            val intent = Intent(this@editProfileActivity, profile_activity::class.java)
            startActivity(intent)
        }

    }
}