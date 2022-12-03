package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("Leaderboard")
class Leaderboard : ParseObject() {
    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun getSet(): ParseObject? {
        return getParseObject(Leaderboard.KEY_SET)
    }

    fun setSet(set : StudySet) {
        put(Leaderboard.KEY_SET, set)
    }

    fun getScore(): Int? {
        return getInt(Leaderboard.KEY_SCORE)
    }

    fun setScore(score: Int) {
        put(Leaderboard.KEY_SCORE, score)
    }

    fun getTotal(): Int? {
        return getInt(Leaderboard.KEY_TOTAL)
    }

    fun setTotal(total: Int) {
        put(Leaderboard.KEY_TOTAL, total)
    }

    companion object {
        const val KEY_SCORE = "Score"
        const val KEY_SET = "StudySet"
        const val KEY_USER = "User"
        const val KEY_TOTAL = "setTotal"
    }
}