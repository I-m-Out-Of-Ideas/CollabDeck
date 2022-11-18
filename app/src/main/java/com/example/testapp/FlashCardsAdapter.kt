package com.example.testapp

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.time.zone.ZoneOffsetTransitionRule

class FlashCardsAdapter(private var flashcards : ArrayList<FlashCard>, private val context : Context, private val editable : Boolean) : RecyclerView.Adapter<FlashCardsAdapter.ViewHolder>() {

    lateinit var flashCard : FlashCard

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        var view: View?
        if (!editable)
            view = inflater.inflate(R.layout.flashcard , parent , false)
        else
            view = inflater.inflate(R.layout.edit_flashcard , parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        flashCard = flashcards.get(position)
        holder.bind(flashCard)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

    fun remove(position: Int) {
        flashcards.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, flashcards.size)
    }

    inner class ViewHolder(itemView : View?) : RecyclerView.ViewHolder(itemView!!) {
        private val term = itemView?.findViewById<TextView>(R.id.id_term)
        private val definition = itemView?.findViewById<TextView>(R.id.id_definition)
        private val edit = itemView?.findViewById<ImageView>(R.id.id_editBtn)
        private val delete = itemView?.findViewById<ImageView>(R.id.id_delBtn)

        fun bind(flashCard : FlashCard) {
            term?.text = flashCard.getTerm()
            definition?.text = flashCard.getDefinition()

            edit?.setOnClickListener {
                Log.d("ACTIVITY" , "edit clicked")
                val intent = Intent(context , EditFlashCard::class.java)
                intent.putExtra("flashcard" , flashCard)
                (context as EditSet).startActivityForResult(intent, EDIT_FLASH_CARD_CODE)
            }

            delete?.setOnClickListener {
                Log.d("ACTIVITY" , "delete clicked")
                (context as EditSet).deleteFlashCard(flashCard)
                remove(adapterPosition)
            }

        }

    }

    companion object {
        val EDIT_FLASH_CARD_CODE = 1234
    }
}