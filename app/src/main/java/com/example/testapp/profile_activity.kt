package com.example.testapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.parse.Parse
import com.parse.ParseUser
import java.io.File

class profile_activity : AppCompatActivity() {

    lateinit var user_name: TextView
    val user = ParseUser.getCurrentUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        user_name = findViewById(R.id.userName)
        user_name.text = user.username.toString()

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