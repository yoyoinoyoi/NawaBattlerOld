package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import com.example.kotori.data.AllCard
import java.io.*

class LobbyActivity : AppCompatActivity() {

    var clickDeck = -1


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "ロビー"

    }

    fun onClickDeck(view: View){

        // クリックしたデッキの番号を記憶しておく
        clickDeck = when(view.id){
            R.id.deckButton1 -> 1
            R.id.deckButton2 -> 2
            R.id.deckButton3 -> 3
            R.id.deckButton4 -> 4
            R.id.deckButton5 -> 5
            R.id.deckButton6 -> 6
            R.id.deckButton7 -> 7
            R.id.deckButton8 -> 8
            else -> -1
        }


        val internal = applicationContext.filesDir
        val file = File(internal, "data$clickDeck")

        // ファイルが無ければ作成する
        if (!file.exists()){
            val bufferedWriter = file.bufferedWriter()
            bufferedWriter.write("0\n1\n2\n3\n4\n5\n6\n7")
            bufferedWriter.close()
            println("Create Deck")
//            bufferedWriter.use { bw -> bw.write("0\n1\n2\n3\n4\n5\n6\n7") }
        }

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()
        var cardIndex = 0

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            val myImage: ImageButton = findViewById(indexToButton(cardIndex))
            myImage.setBackgroundResource(AllCard[cardId].Image)
            cardIndex++
        }
        // クリックしたデッキのカード一覧を下に表示する

    }

    fun onClickBattle(view: View){
        // クリックしたらclickDeck に従ってデッキ編集画面に遷移
        if (clickDeck == -1){
            return
        }
        val intent = Intent(this, BattleActivity::class.java)
        intent.putExtra("deckId",clickDeck.toString())
        startActivity(intent)
    }

    private fun indexToButton(index: Int): Int{
        val button = when(index) {
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
        return button
    }

}