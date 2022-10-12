package com.example.kotori

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.kotori.data.AllCard
import java.io.File
import kotlin.collections.ArrayList

class SelectActivity : AppCompatActivity() {

    // DeckActivity でクリックされたデッキの番号
    var deckId = ""

    // 暫定のデッキ内容
    val cmpDeck: ArrayList<Int> = arrayListOf()

    // 選択されたカード
    var selectedCard = -1

    // カード選択フラグ
    var cardFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "カード選択"

        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerView.layoutManager = GridLayoutManager(
            this, 3, RecyclerView.VERTICAL, false
        )

        val images: ArrayList<Int> = arrayListOf()
        for (i in 0 until AllCard.size) {
            images.add(AllCard[i].Image)
        }

        val adapter = MyAdapter(images)
        recyclerView.adapter = adapter

        deckId = intent.getStringExtra("deckId")!!
        println(deckId)

        // クリックされたデッキのカードを下に表示する
        val internal = applicationContext.filesDir
        val file = File(internal, "data$deckId")

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()
        var cardIndex = 0

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            val myImage: ImageButton = findViewById(indexToButton(cardIndex))
            myImage.setBackgroundResource(AllCard[cardId].Image)
            cmpDeck.add(cardId)
            cardIndex++
        }

        updateDeck()

        // クリックしたときに実行する関数
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                println(position.toString())
                println(view.toString())
                println(clickedText.toString())
                selectedCard = position
                cardFlag = true
            }
        })
    }

    // 変更を確定
    fun onClickOK(view: View) {

        val internal = applicationContext.filesDir
        val file = File(internal, "data$deckId")

        val bufferedWriter = file.bufferedWriter()
        cmpDeck.forEach(){
            println(it.toString())
            bufferedWriter.write(it.toString())
            bufferedWriter.newLine()
        }
        bufferedWriter.close()
    }

    // 画面下のカードをクリックしたときに実行される関数
    fun onClickSelect(view: View) {
        if (!cardFlag) {
            return
        }
        val clickCardId = buttonToIndex(view)
        cmpDeck[clickCardId] = selectedCard
        cardFlag = false
        updateDeck()
        return
    }

    private fun updateDeck() {
        for (i in 0 until cmpDeck.size) {
            val myImage: ImageButton = findViewById(indexToButton(i))
            myImage.setBackgroundResource(AllCard[cmpDeck[i]].Image)
        }
    }

    private fun indexToButton(index: Int): Int {
        val button = when (index) {
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

    private fun buttonToIndex(view: View): Int {
        val index = when (view.id) {
            R.id.deckCard1 -> 0
            R.id.deckCard2 -> 1
            R.id.deckCard3 -> 2
            R.id.deckCard4 -> 3
            R.id.deckCard5 -> 4
            R.id.deckCard6 -> 5
            R.id.deckCard7 -> 6
            R.id.deckCard8 -> 7
            else -> -1
        }
        return index
    }
}