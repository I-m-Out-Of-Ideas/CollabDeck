package com.example.testapp

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser
import java.util.*

@ParseClassName("StudySet")
class StudySet : ParseObject() {

    fun getCollaborators() : List<ParseUser>? {
        return getList("setCollaborators")
    }

    fun addCollaborator(user : ParseUser) {
        add(KEY_COLLABORATOR , user)
    }

    fun removeCollaborator(user : ParseUser) {
        removeAll(KEY_COLLABORATOR, Arrays.asList(user))
    }

    fun getSetName() : String? {
        return getString(KEY_SETNAME)
    }

    fun setSetName(setName : String) {
        put(KEY_SETNAME , setName)
    }

    companion object {
        const val KEY_COLLABORATOR = "setCollaborators"
        const val KEY_SETNAME = "setName"
    }

}