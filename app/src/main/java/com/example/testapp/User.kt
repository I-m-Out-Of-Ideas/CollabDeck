package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseUser

@ParseClassName("_User")
class User : ParseUser() {

    override fun toString(): String {
        return fetchIfNeeded().get("username") as String
    }
}