package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("FlashCard")
class FlashCard : ParseObject() {

    fun getSet() : StudySet? {
        return getParseObject(KEY_STUDYSET) as StudySet
    }

    fun setSet(set : StudySet) {
        put(KEY_STUDYSET , set)
    }

    fun getTerm() : String? {
        return getString(KEY_TERM)
    }

    fun setTerm(term : String) {
        put(KEY_TERM , term)
    }

    fun getDefinition() : String? {
        return getString(KEY_DEFINITION)
    }

    fun setDefinition(definition : String) {
        put(KEY_DEFINITION , definition)
    }

    companion object {
        const val KEY_STUDYSET = "studySet"
        const val KEY_TERM = "term"
        const val KEY_DEFINITION = "definition"
    }

}