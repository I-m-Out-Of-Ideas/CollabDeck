package com.example.testapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.parse.*
import java.util.*


class CollaboratorsActivity : AppCompatActivity() {

    lateinit var collaboratorsRV : RecyclerView
    lateinit var add : FloatingActionButton
    lateinit var back : Button

    lateinit var adapter : CollaboratorsAdapter
    lateinit var mAdapter : CollabRVAdapter
    var collaborators : ArrayList<User> = ArrayList()
    var set : StudySet? = null
    var users : ArrayList<User> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collaborators)

        set = intent?.getParcelableExtra("set")
        Log.i("ACTIVITY", "set name ${set?.getSetName()} set collab ${set?.getCollaborators()?.get(0)?.getString("username")}")

        add = findViewById(R.id.id_addBtn)
        back = findViewById(R.id.id_backBtn)
        collaboratorsRV = findViewById(R.id.id_collaboratorsRV)

        adapter = CollaboratorsAdapter(collaborators , this , set?.objectId!!)
        collaboratorsRV.adapter = adapter
        collaboratorsRV.layoutManager = LinearLayoutManager(this)

        queryCollaborators(set)

        add.setOnClickListener {
            Log.d("ACTIVITY", "add clicked")
            val mAlertDialogBuilder = AlertDialog.Builder(this)
            mAlertDialogBuilder.setTitle("Add a collaborator to ${set?.getSetName()}")
                .setCancelable(false)
                .setPositiveButton("Add", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val selected : User? = mAdapter.selected
                        Log.d("ACTIVITY" , "selected $selected")
                        val query: ParseQuery<StudySet> = ParseQuery.getQuery(StudySet::class.java)
                        query.getInBackground(set?.objectId, object : GetCallback<StudySet> {
                            override fun done(studySet: StudySet, e: ParseException?) {
                                if (e == null) {
                                    Log.d("ACTIVITY" , "get studyset success")
                                    studySet.addCollaborator(selected!!)
                                    studySet?.saveInBackground {

                                        if (it == null) {
                                            Log.d("ACTIVITY", "save studyset success")
                                            set = studySet
                                            collaborators.add(selected!!)
                                            adapter.notifyDataSetChanged()
                                        } else {
                                            Log.d("ACTIVITY", "save studyset failure")
                                        }
                                    }
                                }
                                else {
                                    Log.d("ACTIVITY" , "get studyset fail")
                                }
                            }
                        })
                    }
                })
                .setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }
                })

            val mRowList = layoutInflater.inflate(R.layout.collab_search, null)

            val searchLayout = mRowList.findViewById<RelativeLayout>(R.id.id_searchLayout)
            val search = mRowList.findViewById<SearchView>(R.id.id_search)
            val mRecycler = mRowList.findViewById<RecyclerView>(R.id.id_collabList)
            val cardLayout = mRowList.findViewById<CardView>(R.id.id_cardLayout)

            val query : ParseQuery<ParseUser> = ParseUser.getQuery()
            query.findInBackground(object : FindCallback<ParseUser> {
                override fun done(userList: MutableList<ParseUser>?, e: ParseException?) {
                    if (e != null) {
                        Log.d("ACTIVITY", "users get failure ${e}")
                    } else {
                        Log.d("ACTIVITY", "users get success")
                        if (userList != null) {
                            Log.d("ACTIVITY", "users not empty")
                            users.clear()
                            users.addAll(userList as MutableList<User>)

                            Log.d("ACTIVITY" , "users ${users}")

                            mAlertDialogBuilder.setView(mRowList)
                            val dialog = mAlertDialogBuilder.create()

                            mAdapter = CollabRVAdapter(this@CollaboratorsActivity, users, collaborators, searchLayout, cardLayout, dialog)
                            mRecycler.adapter = mAdapter
                            mRecycler.layoutManager = LinearLayoutManager(this@CollaboratorsActivity)

                            dialog.show()
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

                            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(query: String?): Boolean {
                                    return false
                                }

                                override fun onQueryTextChange(newText: String): Boolean {
                                    filter(newText)
                                    return false
                                }

                            })

                            if (!search.isFocused)
                                filter("")
                        }
                        else
                            Log.d("ACTIVITY", "users empty")
                    }
                }

            })



        }

        back.setOnClickListener {
            Log.d("ACTIVITY" , "back button clicked")
            val intent = Intent(this , StudySetActivity::class.java)
            intent.putExtra("set" , set)
            startActivity(intent)
        }

    }

    fun filter(text: String) {
        val filteredlist: ArrayList<User> = ArrayList<User>()

        if (text.isEmpty()) {}
        else {
            for (item in users) {
                if (item.username.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
            if (filteredlist.isEmpty()) {
                val toast = Toast.makeText(this , "No Data Found..", Toast.LENGTH_SHORT);
                toast.view?.setBackgroundColor(Color.RED)
                toast.show()
            }
        }
        mAdapter.filterList(filteredlist)
    }

    fun queryCollaborators(set : StudySet?) {
        collaborators.clear()
        collaborators.addAll(set?.getCollaborators() as Collection<User>)
        adapter.notifyDataSetChanged()
        Log.d("ACTIVITY" , "queryCollaborators complete ${collaborators}")
    }

}