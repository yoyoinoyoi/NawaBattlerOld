package com.example.kotori.method

import com.example.kotori.R

fun convertIndexToImage(deck: Int): Int {
    val ret = when (deck) {
        0 -> R.id.deckCard1
        1 -> R.id.deckCard2
        2 -> R.id.deckCard3
        3 -> R.id.deckCard4
        4 -> R.id.deckCard5
        5 -> R.id.deckCard6
        6 -> R.id.deckCard7
        7 -> R.id.deckCard8
        else -> R.id.deckCard1
    }
    return ret
}
