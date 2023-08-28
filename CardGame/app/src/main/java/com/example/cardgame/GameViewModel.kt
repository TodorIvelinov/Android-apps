package com.example.cardgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val _listOFCard: MutableLiveData<List<MemoryCard>> = MutableLiveData((0..20).map {
        MemoryCard(R.drawable.card_back_img)
    })
    val listOFCard: LiveData<List<MemoryCard>>
        get() = _listOFCard

    fun setListOfCards(cards: List<Int>) {
        val visibleCards: List<MemoryCard> = cards.map { identifier ->
            MemoryCard(
                identifier = identifier,
                isFaceUp = false,
                isMatched = false,
                isVisible = true
            )
        }
        _listOFCard.value = visibleCards.plus(
            listOFCard.value?.subList(cards.size, listOFCard.value?.lastIndex?.plus(1) ?: (0))
                .orEmpty()
        ).toMutableList()
    }

    fun onCardClicked(cardIndex: Int) {
        if (listOFCard.value?.filter { it.isFaceUp }?.size == 2) {
            listOFCard.value?.forEach {
                if (it.isFaceUp) it.isFaceUp = false
            }
            updateUI()
        }
        if (listOFCard.value?.get(cardIndex)?.isFaceUp == true) return

        if (listOFCard.value?.any { it.isFaceUp } == true) {
            listOFCard.value?.get(cardIndex)?.isFaceUp = true
            var previousCardIndex = -1
            listOFCard.value?.forEachIndexed { index, memoryCard ->
                if (index != cardIndex && memoryCard.isFaceUp) {
                    previousCardIndex = index
                }
            }
            if (listOFCard.value?.get(previousCardIndex)?.identifier == listOFCard.value?.get(
                    cardIndex
                )?.identifier
            ) {
                listOFCard.value?.get(previousCardIndex)?.apply {
                    isMatched = true
                    isFaceUp = true
                }
                listOFCard.value?.get(cardIndex)?.apply {
                    isMatched = true
                    isFaceUp = true
                }
            }
        } else {
            listOFCard.value?.get(cardIndex)?.isFaceUp = true
        }
        listOFCard.value?.forEach {
            if (it.isMatched) {
                it.isFaceUp = false
                it.isVisible = false
            }
        }
        updateUI()
        if (listOFCard.value?.all { !it.isVisible } == true) completeGame()
    }

    private fun updateUI() {
        _listOFCard.value = listOFCard.value
    }

    private fun completeGame() {
        _eventGameFinished.value = true
    }
}
