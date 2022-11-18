package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.widget.Button
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // takes user to logout confirmation screen
        findViewById<Button>(R.id.logout_button).setOnClickListener {
            val intent = Intent(this@MainActivity, logoutConfirmationActivity::class.java)
            startActivity(intent)
        }

        // take user to profile screen
        findViewById<Button>(R.id.profile_button).setOnClickListener {
            val intent = Intent(this@MainActivity, profile_activity::class.java)
            startActivity(intent)
        }

    }
}