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
import com.example.kotori.method.convertDeckToId
import com.example.kotori.method.convertIdToDeck
import java.io.*

class LobbyActivity : AppCompatActivity() {

    var clickDeck = -1

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        // 上に表示するやつ
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "ロビー"

        // 戻るボタンをつけるためのもの
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // 戻るボタンをクリックしたときの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

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

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()
        var cardIndex = 0

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            val myImage: ImageButton = findViewById(convertDeckToId(cardIndex))
            myImage.setBackgroundResource(AllCard[cardId].Image)
            cardIndex++
        }
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

}