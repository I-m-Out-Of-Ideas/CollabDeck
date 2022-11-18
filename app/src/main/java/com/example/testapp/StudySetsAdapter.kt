package com.example.testapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery

class StudySetsAdapter(private var studysets : List<StudySet>, private val context : Context) : RecyclerView.Adapter<StudySetsAdapter.ViewHolder>() {

    lateinit var studySet: StudySet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.studyset, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        studySet = studysets.get(position)
        holder.bind(studySet)
    }

    override fun getItemCount(): Int {
        return studysets.size
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val setName = itemView?.findViewById<TextView>(R.id.id_setName)
        private val termsCount = itemView?.findViewById<TextView>(R.id.id_termsCount)
        private val terms = itemView?.findViewById<TextView>(R.id.id_terms)
        private val setCreator = itemView?.findViewById<TextView>(R.id.id_setCreator)

        fun bind(set: StudySet) {
            setName?.text = set.getSetName()
            setCreator?.text = set.getCollaborators()?.get(0)?.fetchIfNeeded()?.username

            val query : ParseQuery<FlashCard> = ParseQuery.getQuery(FlashCard::class.java)
            query.include(FlashCard.KEY_TERM)
            query.include(FlashCard.KEY_DEFINITION)
            query.whereEqualTo(FlashCard.KEY_STUDYSET, set);
            query.addDescendingOrder("createdAt")
            query.findInBackground(object : FindCallback<FlashCard> {
                override fun done(cardsList: MutableList<FlashCard>?, e: ParseException?) {
                    if (e != null) {
                        Log.d("ACTIVITY", "flashcards get failure ${e}")
                    } else {
                        Log.d("ACTIVITY", "flashcards get success")
                        //Log.d("ACTIVITY" , "cardsList ${cardsList}")
                        if (cardsList != null) {
                            if (cardsList.size == 1)
                                terms?.text = " term"
                            else
                                terms?.text = " terms"
                            termsCount?.text = cardsList.size.toString()
                        }
                    }
                }
            })

            itemView.setOnClickListener {
                val intent = Intent(context , StudySetActivity::class.java)
                intent.putExtra("set" , set)
                context.startActivity(intent)
            }

        }

    }
}