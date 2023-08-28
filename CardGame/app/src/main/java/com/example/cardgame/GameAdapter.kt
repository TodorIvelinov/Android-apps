package com.example.cardgame

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.cardgame.databinding.ItemCardBinding

private const val CARD_ANIMATION_DURATION = 300L

class GameAdapter(private val itemClickListener: IOnCardClickListener) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

	private var memoryCards: List<MemoryCard>? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		ViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		memoryCards?.let { cards ->
			holder.bind(card = cards[position], position)
		}
	}

	override fun getItemCount(): Int = memoryCards?.size ?: 0

	fun updateData(newCards: List<MemoryCard>) {
		memoryCards = newCards
	}

	inner class ViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

		fun bind(card: MemoryCard, position: Int) {
			with(binding) {
				memoryCard = card
				executePendingBindings()
				cardItemIb.setOnClickListener {
					animateDataSetChanged(cardItemContainer)
					itemClickListener.onCardClick(position)
				}
			}
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	fun animateDataSetChanged(itemView: View) {
		val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_out)
		anim.duration = CARD_ANIMATION_DURATION
		anim.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationStart(animation: Animation) {
				//not used
			}

			override fun onAnimationEnd(animation: Animation) {
				notifyDataSetChanged()
				val fadeInAnim = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_in)
				fadeInAnim.duration = CARD_ANIMATION_DURATION
				itemView.startAnimation(fadeInAnim)
			}

			override fun onAnimationRepeat(animation: Animation) {
				//not used
			}
		})

		itemView.startAnimation(anim)
	}
}
