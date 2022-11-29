package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

// User: User
// flashcard : FlashCard
// comment : String

@ParseClassName("Comments")
class Comment : ParseObject() {

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getFlashcard(): ParseObject? {
        return getParseObject(KEY_FLASHCARD)
    }

    fun setFlashcard(flashcard : FlashCard) {
        put(KEY_FLASHCARD, flashcard)
    }

    fun getComment(): String? {
        return getString(KEY_COMMENT)
    }

    fun setComment(comment: String) {
        put(KEY_COMMENT, comment)
    }



    companion object {
        const val KEY_COMMENT = "comment"
        const val KEY_FLASHCARD = "cardID"
        const val KEY_USER = "username"
    }
}