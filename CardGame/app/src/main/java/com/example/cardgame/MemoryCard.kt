package com.example.cardgame

data class MemoryCard(
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false,
    var isVisible: Boolean = false
)
