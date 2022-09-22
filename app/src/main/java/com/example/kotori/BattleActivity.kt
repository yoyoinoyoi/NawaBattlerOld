package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class BattleActivity : AppCompatActivity() {
    data class Card(val Image: Int, val Range: Array<IntArray>)

    // グリッド情報
    var gridmap_base = Array(6){ Array(5){condition.Empty} }
    // グリッドで選択されたマスの座標. (-1, -1) は初期値
    var selectedCoordinate = intArrayOf(-1, -1)
    // プレイヤー1のデッキ
    var player1deck = mutableListOf<Card>()
    // プレイヤー1のデッキ内で、まだ使われていないカードのid
    val nousecard1 = mutableListOf<Int>()
    // プレイヤー2のデッキ
    var player2deck = arrayOf<Card>()
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
    // 選択できるカードの候補
    public var candidatecard = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_field)
        val gridmap = GridMap(gridmap_base)
        // 盤面の状態を初期化
        updategrid(gridmap.gridmap)

        val a1 = intArrayOf(0, 0, 1, 0, 0)
        val b1 = intArrayOf(0, 1, 0, 1, 0)
        val c1 = intArrayOf(1, 0, 0, 0, 1)
        val d1 = intArrayOf(0, 1, 0, 1, 0)
        val e1 = intArrayOf(0, 0, 1, 0, 0)
        val grid1 = arrayOf(a1, b1, c1, d1, e1)
        player1deck.add(Card(R.drawable.pallagon, grid1))
        nousecard1.add(0)

        val a2 = intArrayOf(0, 0, 1, 0, 0)
        val b2 = intArrayOf(0, 1, 1, 1, 0)
        val c2 = intArrayOf(1, 0, 1, 0, 1)
        val d2 = intArrayOf(0, 0, 1, 0, 0)
        val e2 = intArrayOf(0, 0, 1, 0, 0)
        val grid2 = arrayOf(a2, b2, c2, d2, e2)
        player1deck.add(Card(R.drawable.parasol, grid2))
        nousecard1.add(1)

        val a3 = intArrayOf(0, 1, 1, 1, 0)
        val b3 = intArrayOf(0, 1, 1, 0, 0)
        val c3 = intArrayOf(0, 1, 0, 0, 0)
        val d3 = intArrayOf(0, 1, 0, 0, 0)
        val e3 = intArrayOf(0, 1, 0, 0, 0)
        val grid3 = arrayOf(a3, b3, c3, d3, e3)
        player1deck.add(Card(R.drawable.flag, grid3))
        nousecard1.add(2)

        val a4 = intArrayOf(0, 0, 0, 0, 0)
        val b4 = intArrayOf(0, 1, 0, 1, 0)
        val c4 = intArrayOf(1, 1, 1, 1, 1)
        val d4 = intArrayOf(0, 1, 0, 1, 0)
        val e4 = intArrayOf(0, 0, 0, 0, 0)
        val grid4 = arrayOf(a4, b4, c4, d4, e4)
        player1deck.add(Card(R.drawable.crab, grid4))
        nousecard1.add(3)

        val a5 = intArrayOf(0, 0, 0, 0, 0)
        val b5 = intArrayOf(0, 1, 1, 1, 0)
        val c5 = intArrayOf(0, 1, 1, 1, 1)
        val d5 = intArrayOf(0, 0, 0, 1, 0)
        val e5 = intArrayOf(0, 0, 1, 0, 0)
        val grid5 = arrayOf(a5, b5, c5, d5, e5)
        player1deck.add(Card(R.drawable.thinking, grid5))
        nousecard1.add(4)

        setcard()
    }

    // グリッドをクリックしたときに実行される関数
    @SuppressLint("SetTextI18n")
    fun onClickGrid(view: View){

        // カードが選択されている場合のみ作動する
//        if (!cardflag){
//            return
//        }

        // クリックしたボタンを座標に変換
        val index = idtoindex(view)

        // 今回選択した位置が一致していれば確定する

//        if (index === selectedCoordinate){
//
//        }

        // 座標を保持しておく
        selectedCoordinate = index

        if (nowTurn == condition.Player1){
            nowTurn = condition.Player2
        }
        else{
            nowTurn = condition.Player1
        }

    }
    // 画面下のカードをクリックしたとき、その情報を引き渡す
    fun onClickCard(view: View?){
        //動作確認
        finish()

        // グリッドを選択していたら解除する
        if (gridflag) {
            gridflag = false
        }

        // カード情報が格納されている場合にはそれを破棄する(選択解除)
        // 破棄ではなく、cardflag をfalseにする
        if (cardflag) {
            cardflag = false
            return
        }
        // カード情報がまだ格納されていないなら、格納する
        //


    }
    // gridmap からフロントへの更新を行う
    private fun updategrid (gridmap: Array<Array<condition>>) {
        val gridcolumn = gridmap.size
        val gridrow = gridmap[0].size
        for (i in 0 until gridcolumn) {
            for (j in 0 until gridrow) {
                // val arr = intArrayOf(i, j)
                val imageButtonId = indextoid(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                // println(i.toString() +j.toString() +imageButtonId)
                // gridmap の状態によって色を更新する
                when (gridmap[i][j]) {
                    condition.Empty -> myImage.setBackgroundResource(R.drawable.gray)
                    condition.Player1 -> myImage.setBackgroundResource(R.drawable.blue)
                    condition.Player2 -> myImage.setBackgroundResource(R.drawable.yellow)
                }
            }
        }
    }

    // デッキからカードをランダムで選んで設置する
    private fun setcard () {
        // 枚数が足りない場合にはwhite で対応する
        val myImage1: ImageButton = findViewById (R.id.cardbutton1)
        val myImage2: ImageButton = findViewById (R.id.cardbutton2)
        val myImage3: ImageButton = findViewById (R.id.cardbutton3)
        if (nousecard1.isNotEmpty()) {
            val leng = nousecard1.size
            val selectedcard1 = nousecard1[(0 until leng).random()]
            myImage1.setBackgroundResource(player1deck[selectedcard1].Image)
            nousecard1.remove(selectedcard1)
        }
        else{
            myImage1.setBackgroundResource(R.drawable.white)
        }

        if (nousecard1.isNotEmpty()) {
            val leng = nousecard1.size
            val selectedcard2 = nousecard1[(0 until leng).random()]
            myImage2.setBackgroundResource(player1deck[selectedcard2].Image)
            nousecard1.remove(selectedcard2)
        }
        else{
            myImage2.setBackgroundResource(R.drawable.white)
        }

        if (nousecard1.isNotEmpty()) {
            val leng = nousecard1.size
            val selectedcard3 = nousecard1[(0 until leng).random()]
            myImage3.setBackgroundResource(player1deck[selectedcard3].Image)
            nousecard1.remove(selectedcard3)
        }
        else{
            myImage3.setBackgroundResource(R.drawable.white)
        }

    }
}