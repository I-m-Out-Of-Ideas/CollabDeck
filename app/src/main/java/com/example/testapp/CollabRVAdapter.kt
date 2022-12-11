package com.example.testapp

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parse.ParseUser


class CollabRVAdapter(private val context: Context, private var users: ArrayList<ParseUser>, private var collaborators: ArrayList<ParseUser>, private var searchLayout: RelativeLayout, private var cardLayout: CardView, private var dialog: AlertDialog) : RecyclerView.Adapter<CollabRVAdapter.ViewHolder>() {

    lateinit var user : ParseUser
    var selected : ParseUser? = null

    fun filterList(filterList: ArrayList<ParseUser>) {
        users = filterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.add_collab_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImg = itemView.findViewById<ImageView>(R.id.id_profileImg)
        private val username = itemView.findViewById<TextView>(R.id.id_username)
        private val subtitle = itemView.findViewById<TextView>(R.id.id_subtitle)

        fun bind(user : ParseUser) {
            username.text = user.username

            Glide.with(context).load(user.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(context).load(R.drawable.loading)).apply(RequestOptions.circleCropTransform()).into(profileImg)

            if (collaborators.toString().contains(user.toString())) {
                subtitle.text = "Already has access to this set"
                itemView.setOnClickListener(null)
            }
            else {
                subtitle.text = "Invite collaborator"
                itemView.setOnClickListener{
                    Log.d("ACTIVITY", "clicked on $user")

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

                    searchLayout.visibility = View.GONE

                    val profImg : ImageView = (cardLayout.getChildAt(0) as RelativeLayout).getChildAt(0) as ImageView
                    val username : TextView = (cardLayout.getChildAt(0) as RelativeLayout).getChildAt(1) as TextView
                    val remove : ImageView = (cardLayout.getChildAt(0) as RelativeLayout).getChildAt(2) as ImageView

                    Glide.with(context).load(user.getParseFile("pfp")?.url).placeholder(R.drawable.loading).thumbnail(Glide.with(context).load(R.drawable.loading)).apply(RequestOptions.circleCropTransform()).into(profImg)
                    username?.text = user.username
                    cardLayout.visibility = View.VISIBLE
                    selected = user

                    remove.setOnClickListener {
                        cardLayout.visibility = View.GONE
                        (searchLayout.getChildAt(0) as SearchView).setQuery("", false)
                        searchLayout.visibility = View.VISIBLE
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    }
                }
            }

        }

    }
}