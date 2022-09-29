package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text

class BattleActivity : AppCompatActivity() {

    // どちらのターンかを識別する
    var nowTurn = condition.Player1
    // 現在グリッドを操作しているか
    var gridflag = false
    // 現在カードを操作しているか
    var cardflag = false
    // 選択されたカードの識別番号
    var selectedCardId = -1
    // 選択されたカードの能力(回転させるときなどに一時的に保持するため)
    var selectedCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0) }
    // 全ターン数
    val totalTurn = 5
    // 今のターン数
    var nowTurnCount = 1
    // Player1 のスコア
    var player1Score = 0
    // Player2 のスコア
    var player2Score = 0


    // 初期化

    // グリッド情報
    var gridmap_base = Array(12){ Array(10){condition.Empty} }
    val gridmap = GridMap(gridmap_base)
    val deckField1 = DeckField()
    val deckField2 = DeckField()

    @SuppressLint("SetTextI18n")
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
        deckField1.deck.add(Card(R.drawable.palagon, grid1))
        deckField2.deck.add(Card(R.drawable.palagon, grid1))

        val a2 = intArrayOf(0, 0, 1, 0, 0)
        val b2 = intArrayOf(0, 1, 1, 1, 0)
        val c2 = intArrayOf(1, 0, 1, 0, 1)
        val d2 = intArrayOf(0, 0, 1, 0, 0)
        val e2 = intArrayOf(0, 1, 0, 0, 0)
        val grid2 = arrayOf(a2, b2, c2, d2, e2)
        deckField1.deck.add(Card(R.drawable.parasol, grid2))
        deckField2.deck.add(Card(R.drawable.parasol, grid2))

        val a3 = intArrayOf(0, 0, 0, 0, 0)
        val b3 = intArrayOf(0, 0, 0, 1, 0)
        val c3 = intArrayOf(0, 0, 1, 0, 0)
        val d3 = intArrayOf(0, 0, 0, 1, 0)
        val e3 = intArrayOf(0, 0, 0, 0, 0)
        val grid3 = arrayOf(a3, b3, c3, d3, e3)
        deckField1.deck.add(Card(R.drawable.boomerang, grid3))
        deckField2.deck.add(Card(R.drawable.boomerang, grid3))

        val a6 = intArrayOf(0, 0, 0, 0, 0)
        val b6 = intArrayOf(0, 0, 1, 0, 0)
        val c6 = intArrayOf(0, 1, 1, 1, 0)
        val d6 = intArrayOf(0, 0, 1, 0, 0)
        val e6 = intArrayOf(0, 0, 0, 0, 0)
        val grid6 = arrayOf(a6, b6, c6, d6, e6)
        deckField1.deck.add(Card(R.drawable.bomb, grid6))
        deckField2.deck.add(Card(R.drawable.bomb, grid6))

        val a7 = intArrayOf(0, 1, 1, 0, 0)
        val b7 = intArrayOf(0, 0, 1, 1, 0)
        val c7 = intArrayOf(0, 0, 1, 0, 0)
        val d7 = intArrayOf(0, 0, 1, 0, 0)
        val e7 = intArrayOf(0, 0, 1, 0, 0)
        val grid7 = arrayOf(a7, b7, c7, d7, e7)
        deckField1.deck.add(Card(R.drawable.hinawaju, grid7))
        deckField2.deck.add(Card(R.drawable.hinawaju, grid7))


        // フロントへ更新
        updategrid(gridmap.gridmap)
        deckField1.reload()
        deckField2.reload()
        setCard(deckField1.imageArray())
    }

    // グリッドをクリックしたときに実行される関数
    fun onClickGrid(view: View){

        // カードが選択されていなければ何もしない
        if (!cardflag){
            return
        }

        // クリックしたボタンを座標に変換
        val index = idtoindex(view)
        // 置ければ置く
        if ( gridmap.canset(index, selectedCardRange, nowTurn) ){
            gridmap.setcolor(index, selectedCardRange, nowTurn)
            deckField1.choice(selectedCardId)
            updategrid(gridmap.gridmap)
            setCard(deckField1.imageArray())
            changePlayer()

            computerTurn()

            cardflag = false
        }
    }

    // 画面下のカードをクリックしたとき、その情報を引き渡す
    fun onClickCard(view: View){

        // クリックしたカードの能力を取り出す
        val clickButton = when(view.id){
            R.id.cardbutton1 -> 0
            R.id.cardbutton2 -> 1
            R.id.cardbutton3 -> 2
            else -> -1
        }

        // カードが何もない時には何もしない
        if (deckField1.handCard[clickButton] == -1){
            cardflag = false
            return
        }

        // 同じカードを連続でクリックした場合にはキャンセルする
        if (cardflag && (clickButton == selectedCardId)){
            cardflag = false
            return
        }

        selectedCardId = clickButton
        selectedCardRange = deckField1.deck[deckField1.handCard[selectedCardId]].Range
        cardflag = true
    }

    // 回転ボタンをクリックしたときに実行される関数
    fun onClickRotate(view: View){
        // カードが選択されなければ実行しない
        if (!cardflag) {
            return
        }
        selectedCardRange = rotateRange(selectedCardRange)
        cardflag = false
    }

    // パスボタンをクリックしたときに実行される関数
    fun onClickPass(view: View){
        if (!cardflag) {
            return
        }
        deckField1.choice(selectedCardId)
        setCard(deckField1.imageArray())
        changePlayer()
        computerTurn()
        cardflag = false
    }

    /* 以下、プライベート関数 */

    // Turn x / N: PlayerName として更新する

    // gridmap からフロントへの更新を行う
    @SuppressLint("SetTextI18n")
    private fun updategrid (gridmap: Array<Array<condition>>) {
        player1Score = 0
        player2Score = 0
        val gridColumn = gridmap.size
        val gridRow = gridmap[0].size
        for (i in 0 until gridColumn) {
            for (j in 0 until gridRow) {
                val imageButtonId = indextoid(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                // println(i.toString() +j.toString() +imageButtonId)
                // gridMap の状態によって色を更新する
                if (gridmap[i][j] == condition.Player1){
                    myImage.setBackgroundResource(R.drawable.blue)
                    player1Score++
                }
                else if (gridmap[i][j] == condition.Player2){
                    myImage.setBackgroundResource(R.drawable.yellow)
                    player2Score++
                }
                else{
                    myImage.setBackgroundResource(R.drawable.gray)
                }
            }
        }
        val turnText: TextView = findViewById (R.id.Turn)
        turnText.text = "Turn $nowTurnCount / $totalTurn : $nowTurn"
        val p1Text: TextView = findViewById (R.id.player1)
        p1Text.text = "$player1Score"
        val p2Text: TextView = findViewById (R.id.player2)
        p2Text.text = "$player2Score"
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

    // Range を右に90度だけ回転させる
    // Range: 5x5 の配列
    private fun rotateRange(Range: Array<IntArray>): Array<IntArray>{
        val newList = Array(5){ intArrayOf(0, 0, 0, 0, 0) }
        for (i in 0 until Range.size){
            for (j in 0 until Range[0].size){
                newList[i][j] = Range[j][Range[0].size -i -1]
            }
        }
        return newList
    }

    // プレイヤーの順番を管理する
    private fun changePlayer(){
        if (nowTurn == condition.Player1){
            nowTurn = condition.Player2
        }
        else{
            nowTurn = condition.Player1
        }
    }

    // コンピュータが実行するとき
    private fun computerTurn() {
        // deck はdeckField2 を用いる

        // 手札からまずカードを決める
        for (choiceCardId in 0 until deckField2.handCard.size){
            // 回転させる
            val choiceCard = deckField2.handCard[choiceCardId]
            if (choiceCard == -1){
                continue
            }
            val choiceRange1 = deckField2.deck[choiceCard].Range
            val choiceRange2 = rotateRange(choiceRange1)
            val choiceRange3 = rotateRange(choiceRange2)
            val choiceRange4 = rotateRange(choiceRange3)

            val candidates = mutableListOf<IntArray>()
            val ranges = mutableListOf<Array<IntArray>>()
            for (i in 0 until gridmap.gridmap.size){
                for (j in 0 until gridmap.gridmap[0].size){
                    if (gridmap.canset(intArrayOf(i, j), choiceRange1, condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange1)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange2, condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange2)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange3, condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange3)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange4, condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange4)
                    }
                }
            }
            if (candidates.isNotEmpty()){
                val randomNum = (candidates.indices).random()
                gridmap.setcolor(candidates[randomNum], ranges[randomNum], condition.Player2)

                deckField2.choice(choiceCardId)
                changePlayer()
                updategrid(gridmap.gridmap)
                return
            }
        }

        // 置けなくなったら適当に選んで捨てる
        for (choiceCardId in 0 until deckField2.handCard.size){
            val choiceCard = deckField2.handCard[choiceCardId]
            if (choiceCard != -1){
                deckField2.choice(choiceCard)
                changePlayer()
                updategrid(gridmap.gridmap)
                return
            }
        }
    }
}