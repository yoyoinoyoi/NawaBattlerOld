package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import com.example.kotori.data.AllCard
import com.example.kotori.method.convertIdToDeck
import java.io.*

class DeckActivity : AppCompatActivity() {

    // クリックしたデッキ番号
    private var clickDeck = -1

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        // 上に表示するやつ
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "デッキ編集"

        // 戻るボタンをつけるためのもの
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 戻るボタンをクリックしたときの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // デッキ編集画面に戻る
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    * デッキをクリックしたときに実行される関数
     */
    fun onClickDeck(view: View){

        // クリックしたデッキの番号を記憶しておく
        clickDeck = convertIdToDeck(view)

        val internal = applicationContext.filesDir
        val file = File(internal, "data$clickDeck")

        // ファイルが無ければ作成する
        if (!file.exists()){
            val bufferedWriter = file.bufferedWriter()
            bufferedWriter.write("0\n1\n2\n3\n4\n5\n6\n7")
            bufferedWriter.close()
            println("Create Deck")
        }

        // ファイルにかかれたカードの画像を画面下に表示する
        val bufferedReader = file.bufferedReader()
        var cardIndex = 0

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            val myImage: ImageButton = findViewById(convertDeckToId(cardIndex))
            myImage.setBackgroundResource(AllCard[cardId].Image)
            cardIndex++
        }
    }

    fun onClickEdit(view: View){
        // クリックしたらclickDeck に従ってデッキ編集画面に遷移
        if (clickDeck == -1){
            return
        }
        val intent = Intent(this, SelectActivity::class.java)
        intent.putExtra("deckId",clickDeck.toString())
        startActivity(intent)
    }

    fun onClickRename(view: View){
        return
    }

    private fun convertDeckToId(index: Int): Int{
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