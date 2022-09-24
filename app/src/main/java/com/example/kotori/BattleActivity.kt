package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class BattleActivity : AppCompatActivity() {

    // グリッド情報
    var gridmap_base = Array(12){ Array(10){condition.Empty} }
    // グリッドで選択されたマスの座標. (-1, -1) は初期値
    var selectedCoordinate = intArrayOf(-1, -1)
    // プレイヤー1のデッキ
    var player1deck = mutableListOf<Card>()
    // どちらのターンかを識別する
    var nowTurn = condition.Player1
    // 現在グリッドを操作しているか
    var gridflag = false
    // 現在カードを操作しているか
    var cardflag = false
    // 選択されたカードの識別番号
    public var selectedCardId = -1
    // 選択されたカードの効果
    public var selectedCardStatus = Array(5){ intArrayOf(0, 0, 0, 0, 0) }

    // 初期化
    val gridmap = GridMap(gridmap_base)
    val deckField1 = DeckField()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_field)

        // デッキのカード情報を入力
        val a1 = intArrayOf(0, 0, 1, 0, 0)
        val b1 = intArrayOf(0, 1, 0, 1, 0)
        val c1 = intArrayOf(1, 0, 0, 0, 1)
        val d1 = intArrayOf(0, 1, 0, 1, 0)
        val e1 = intArrayOf(0, 0, 1, 0, 0)
        val grid1 = arrayOf(a1, b1, c1, d1, e1)
        deckField1.deck.add(Card(R.drawable.pallagon, grid1))

        val a2 = intArrayOf(0, 0, 1, 0, 0)
        val b2 = intArrayOf(0, 1, 1, 1, 0)
        val c2 = intArrayOf(1, 0, 1, 0, 1)
        val d2 = intArrayOf(0, 0, 1, 0, 0)
        val e2 = intArrayOf(0, 0, 1, 0, 0)
        val grid2 = arrayOf(a2, b2, c2, d2, e2)
        deckField1.deck.add(Card(R.drawable.parasol, grid2))

        val a3 = intArrayOf(0, 1, 1, 1, 0)
        val b3 = intArrayOf(0, 1, 1, 0, 0)
        val c3 = intArrayOf(0, 1, 0, 0, 0)
        val d3 = intArrayOf(0, 1, 0, 0, 0)
        val e3 = intArrayOf(0, 1, 0, 0, 0)
        val grid3 = arrayOf(a3, b3, c3, d3, e3)
        deckField1.deck.add(Card(R.drawable.flag, grid3))

        val a4 = intArrayOf(0, 0, 0, 0, 0)
        val b4 = intArrayOf(0, 1, 0, 1, 0)
        val c4 = intArrayOf(1, 1, 1, 1, 1)
        val d4 = intArrayOf(0, 1, 0, 1, 0)
        val e4 = intArrayOf(0, 0, 0, 0, 0)
        val grid4 = arrayOf(a4, b4, c4, d4, e4)
        deckField1.deck.add(Card(R.drawable.crab, grid4))

        val a5 = intArrayOf(0, 0, 0, 0, 0)
        val b5 = intArrayOf(0, 1, 1, 1, 0)
        val c5 = intArrayOf(0, 1, 1, 1, 1)
        val d5 = intArrayOf(0, 0, 0, 1, 0)
        val e5 = intArrayOf(0, 0, 1, 0, 0)
        val grid5 = arrayOf(a5, b5, c5, d5, e5)
        deckField1.deck.add(Card(R.drawable.thinking, grid5))


        // フロントへ更新
        updategrid(gridmap.gridmap)
        deckField1.reload()
        setCard(deckField1.imageArray())
    }

    // グリッドをクリックしたときに実行される関数
    @SuppressLint("SetTextI18n")
    fun onClickGrid(view: View){

        // カードが選択されていなければ何もしない
        if (!cardflag){
            return
        }

        // クリックしたボタンを座標に変換
        val dataText: TextView = findViewById (R.id.result)
        val index = idtoindex(view)
        val selectedRange = deckField1.deck[deckField1.handCard[selectedCardId]].Range

        // 置ければ置く
        if ( gridmap.canset(index, selectedRange, nowTurn) ){
            gridmap.setcolor(index, selectedRange, nowTurn)
            deckField1.choice(selectedCardId)
            updategrid(gridmap.gridmap)
            setCard(deckField1.imageArray())
            changePlayer()
            cardflag = false
        }
        else{
            dataText.text = "そこにはおけません"
        }
    }

    // 画面下のカードをクリックしたとき、その情報を引き渡す
    fun onClickCard(view: View){

        val dataText: TextView = findViewById (R.id.data)

        // クリックしたカードの能力を取り出す
        val clickButton = when(view.id){
            R.id.cardbutton1 -> 0
            R.id.cardbutton2 -> 1
            R.id.cardbutton3 -> 2
            else -> -1
        }

        // カードが何もない時には何もしない
        if (deckField1.handCard[clickButton] == -1){
            dataText.text = "not select"
            cardflag = false
            return
        }

        // 同じカードを連続でクリックした場合にはキャンセルする
        if (cardflag && (clickButton == selectedCardId)){
            dataText.text = "cancel"
            cardflag = false
            return
        }

        selectedCardId = clickButton
        dataText.text = clickButton.toString()
        cardflag = true
    }



    // gridmap からフロントへの更新を行う
    private fun updategrid (gridmap: Array<Array<condition>>) {
        val gridColumn = gridmap.size
        val gridRow = gridmap[0].size
        for (i in 0 until gridColumn) {
            for (j in 0 until gridRow) {
                val imageButtonId = indextoid(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                // println(i.toString() +j.toString() +imageButtonId)
                // gridMap の状態によって色を更新する
                when (gridmap[i][j]) {
                    condition.Empty -> myImage.setBackgroundResource(R.drawable.gray)
                    condition.Player1 -> myImage.setBackgroundResource(R.drawable.blue)
                    condition.Player2 -> myImage.setBackgroundResource(R.drawable.yellow)
                }
            }
        }
    }

    // デッキからカードをランダムで選んで設置する
    private fun setCard (imageList: MutableList<Int>) {
        // 枚数が足りない場合にはwhite で対応する
        val myImage1: ImageButton = findViewById (R.id.cardbutton1)
        val myImage2: ImageButton = findViewById (R.id.cardbutton2)
        val myImage3: ImageButton = findViewById (R.id.cardbutton3)
        myImage1.setBackgroundResource(imageList[0])
        myImage2.setBackgroundResource(imageList[1])
        myImage3.setBackgroundResource(imageList[2])
    }

    private fun changePlayer(){
        if (nowTurn == condition.Player1){
            nowTurn = condition.Player2
        }
        else{
            nowTurn = condition.Player1
        }
    }
}