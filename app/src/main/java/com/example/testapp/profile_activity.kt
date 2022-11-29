package com.example.testapp

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.Parse
import com.parse.ParseUser
import java.io.File

class profile_activity : AppCompatActivity() {

    lateinit var user_name: TextView
    lateinit var user_profile_picture : ImageView
    val user = ParseUser.getCurrentUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        user_name = findViewById(R.id.userName)
        user_profile_picture = findViewById(R.id.user_profile_picture)

        user_name.text = user.username.toString()
        Glide.with(this).load(user.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(this).load(R.drawable.loading)).into(user_profile_picture)

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