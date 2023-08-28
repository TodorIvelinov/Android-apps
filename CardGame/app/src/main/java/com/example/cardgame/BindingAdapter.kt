package com.example.cardgame

import android.view.View
import android.widget.ImageButton
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["memoryCard"])
fun ImageButton.setCard(memoryCard: MemoryCard) {
	this.visibility = if (memoryCard.isVisible) View.VISIBLE else View.GONE
	this.setBackgroundResource(if (memoryCard.isFaceUp) memoryCard.identifier else R.drawable.card_back_img)
}
