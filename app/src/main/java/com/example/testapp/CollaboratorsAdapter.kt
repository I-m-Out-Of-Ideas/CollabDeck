package com.example.testapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.*
import java.util.ArrayList

class CollaboratorsAdapter(private var collaborators: ArrayList<User>, private val context: Context, private var setId: String) : RecyclerView.Adapter<CollaboratorsAdapter.ViewHolder>() {

    lateinit var collaborator: User

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        var view = inflater.inflate(R.layout.collaborator , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        collaborator = collaborators.get(position)
        holder.bind(collaborator)
    }

    override fun getItemCount(): Int {
        return collaborators.size
    }

    fun remove(position: Int) {
        collaborators.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, collaborators.size)
    }

    inner class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        private val profImg = itemView?.findViewById<ImageView>(R.id.id_profileImg)
        private val username = itemView?.findViewById<TextView>(R.id.id_username)
        private val role = itemView?.findViewById<TextView>(R.id.id_role)
        private val delete = itemView?.findViewById<ImageView>(R.id.id_delBtn)

        fun bind(collaborator : User) {
            Glide.with(itemView.context).load(collaborator.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(itemView.context).load(R.drawable.loading)).apply(RequestOptions.circleCropTransform()).into(profImg!!)
            username?.text = collaborator.fetchIfNeeded().username

            if (collaborator == collaborators.get(0)) {
                delete?.visibility = View.INVISIBLE
                role?.text = "Creator"
            }

            delete?.setOnClickListener {
                val query : ParseQuery<StudySet> = ParseQuery.getQuery(StudySet::class.java)
                query.getInBackground(setId, object : GetCallback<StudySet> {
                    override fun done(set: StudySet?, e: ParseException?) {
                        if (e == null) {
                            Log.d("ACTIVITY" , "get set success")
                            val user = collaborators.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            notifyItemRangeChanged(adapterPosition, collaborators.size)
                            set?.removeCollaborator(user)
                            set?.saveInBackground { exception ->
                                if (exception != null) {
                                    Log.d("ACTIVITY", "delete collab failure ${exception}")
                                }
                                else {
                                    Log.d("ACTIVITY", "delete collab success")
                                    Log.d("ACTIVITY" , "user: ${user}")
                                    Log.d("ACTIVITY" , "current user: ${ParseUser.getCurrentUser()}")
                                    (context as CollaboratorsActivity).set = set
                                    if (user.objectId == ParseUser.getCurrentUser().objectId) {
                                        val intent = Intent(context , MainActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        }
                        else {
                            Log.d("ACTIVITY" , "get set failure")
                        }
                    }
                })


            }

        }

    }

}
