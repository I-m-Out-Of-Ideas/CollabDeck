package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.parse.ParseUser

class logoutConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout_confirmation)

        // log user out
        findViewById<Button>(R.id.yes_button).setOnClickListener {
            ParseUser.logOut()
            val intent = Intent(this@logoutConfirmationActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // send user back to previous screen
        findViewById<Button>(R.id.no_button).setOnClickListener {
            val intent = Intent(this@logoutConfirmationActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}