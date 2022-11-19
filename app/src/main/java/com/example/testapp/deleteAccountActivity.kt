package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.parse.ParseUser

class deleteAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)

        // cancel account deletion, send user to previous screen
        findViewById<Button>(R.id.no_button).setOnClickListener {
            val intent = Intent(this@deleteAccountActivity, settingsActivity::class.java)
            startActivity(intent)
        }

        // confirm account deletion, delete account, send user to login page
        findViewById<Button>(R.id.yes_button).setOnClickListener {
            val user = ParseUser.getCurrentUser()
            user.deleteInBackground()
            ParseUser.logOut()
            val intent = Intent(this@deleteAccountActivity, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}