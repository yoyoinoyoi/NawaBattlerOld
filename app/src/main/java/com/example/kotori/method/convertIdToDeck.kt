package com.example.kotori.method

import android.view.View
import com.example.kotori.R

/*
* クリックされたデッキボタンのIDを返す関数
 */
fun convertIdToDeck(view: View): Int{

    // クリックしたデッキの番号を記憶しておく
    val ret = when(view.id){
        R.id.deckButton1 -> 10001
        R.id.deckButton2 -> 10002
        R.id.deckButton3 -> 10003
        R.id.deckButton4 -> 10004
        R.id.deckButton5 -> 10005
        R.id.deckButton6 -> 10006
        R.id.deckButton7 -> 10007
        R.id.deckButton8 -> 10008
        else -> -1
    }
    return ret
}