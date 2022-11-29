package com.example.testapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class FlashcardFragment : Fragment() {

    lateinit var frontAnim: AnimatorSet // for our pretty flip anim
    lateinit var backAnim : AnimatorSet // for our pretty flip anim
    private var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard, container, false)
        val cardFront: TextView = view.findViewById(R.id.card_front)
        val cardBack: TextView = view.findViewById(R.id.card_back)
        val args = this.arguments
        val term = args?.get("term")
        val def = args?.get("def")
        cardFront.text = term.toString()
        cardBack.text = def.toString()

        val scale = requireActivity().resources.displayMetrics.density
        cardFront.cameraDistance = 8000 * scale
        cardBack.cameraDistance = 8000 * scale

        frontAnim = AnimatorInflater.loadAnimator(activity, R.animator.front_animator)
                as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(activity, R.animator.back_animator)
                as AnimatorSet
        return view
    }

    fun flip() {
        val cardFront : TextView = requireView().findViewById(R.id.card_front)
        val cardBack : TextView = requireView().findViewById(R.id.card_back)

        if(isFront)
        {
            frontAnim.setTarget(cardFront)
            backAnim.setTarget(cardBack)
            frontAnim.start()
            backAnim.start()
            isFront = false
        }
        else
        {
            frontAnim.setTarget(cardBack)
            backAnim.setTarget(cardFront)
            backAnim.start()
            frontAnim.start()
            isFront = true
        }
    }
}