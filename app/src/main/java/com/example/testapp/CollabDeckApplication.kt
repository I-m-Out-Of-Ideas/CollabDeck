package com.example.testapp

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject

class CollabDeckApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ParseObject.registerSubclass(StudySet::class.java)

        ParseObject.registerSubclass(FlashCard::class.java)

        ParseObject.registerSubclass(Comment::class.java)

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build())
    }
}