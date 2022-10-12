package com.example.kotori

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.kotori.Module.Card
import com.example.kotori.Module.DeckField
import com.example.kotori.Module.GridMap
import com.example.kotori.Module.Condition
import com.example.kotori.data.AllCard
import java.io.File

class BattleActivity : AppCompatActivity() {

    // 現在グリッドを操作しているか
    var gridflag = false
    // 現在カードを操作しているか
    var cardflag = false
    // 選択されたカードの識別番号
    var selectedCardId = -1
    // 選択されたカードの能力(回転させるときなどに一時的に保持するため)
    var selectedCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0) }
    // 選択されたgrid の座標
    var selectedGridCoordinates = intArrayOf(6, 4)
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
    var gridmap_base = Array(12){ Array(10){ Condition.Empty} }
    val gridmap = GridMap(gridmap_base)
    val deckField1 = DeckField()
    val deckField2 = DeckField()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "バトル!!"

        // 選んだデッキから生成する

        val deckId = intent.getStringExtra("deckId")!!

        // クリックされたデッキのカードを下に表示する
        val internal = applicationContext.filesDir
        val file = File(internal, "data$deckId")

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            deckField1.deck.add(AllCard[cardId])
            deckField2.deck.add(AllCard[cardId])
        }
        
        // フロントへ更新
        deckField1.reload()
        deckField2.reload()
        updategrid(gridmap.gridmap)
        setCard(deckField1.imageArray())
    }

    // グリッドをクリックしたときに実行される関数
    fun onClickGrid(view: View){

        // カードが選択されていなければ何もしない
        if (!cardflag){
            return
        }

        // クリックしたボタンを座標に変換
        val index = idToIndex(view)

        // プレビューを表示する
        if ( !( gridflag && (index[0] == selectedGridCoordinates[0]) && (index[1] == selectedGridCoordinates[1]) ) ){

            selectedGridCoordinates = index
            gridflag = true
            preview()
            return
        }

        // 置ければ置く
        if ( gridmap.canset(index, selectedCardRange, Condition.Player1) ){
            play()
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
            selectedCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
            preview()
            return
        }

        // 同じカードを連続でクリックした場合にはキャンセルする
        if (cardflag && (clickButton == selectedCardId)){
            cardflag = false
            selectedCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
            preview()
            return
        }

        selectedCardId = clickButton
        selectedCardRange = deckField1.deck[deckField1.handCard[selectedCardId]].Range
        cardflag = true
        gridflag = false
        preview()

    }

    // 回転ボタンをクリックしたときに実行される関数
    fun onClickRotate(view: View){
        // カードが選択されなければ実行しない
        if (!cardflag) {
            return
        }
        selectedCardRange = rotateRange(selectedCardRange)
        preview()
    }

    // パスボタンをクリックしたときに実行される関数
    fun onClickPass(view: View){
        if (!cardflag) {
            return
        }
        selectedGridCoordinates = intArrayOf(0, 0)
        selectedCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
        play()
    }

    /* 以下、プライベート関数 */

    // Turn x / N: PlayerName として更新する

    // gridmap からフロントへの更新を行う
    @SuppressLint("SetTextI18n")
    private fun updategrid (gridmap: Array<Array<Condition>>) {
        player1Score = 0
        player2Score = 0
        val gridColumn = gridmap.size
        val gridRow = gridmap[0].size
        for (i in 0 until gridColumn) {
            for (j in 0 until gridRow) {
                val imageButtonId = indexToId(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                // println(i.toString() +j.toString() +imageButtonId)
                // gridMap の状態によって色を更新する
                if (gridmap[i][j] == Condition.Player1){
                    myImage.setBackgroundResource(R.drawable.blue)
                    player1Score++
                }
                else if (gridmap[i][j] == Condition.Player2){
                    myImage.setBackgroundResource(R.drawable.yellow)
                    player2Score++
                }
                else{
                    myImage.setBackgroundResource(R.drawable.gray)
                }
            }
        }
        // スコア更新
        val turnText: TextView = findViewById (R.id.Turn)

        turnText.text = "Turn $nowTurnCount / $totalTurn : " +
                "Card ${deckField1.stack.size + deckField1.handCard.size} / ${deckField1.deck.size}"
        val p1Text: TextView = findViewById (R.id.player1)
        p1Text.text = "$player1Score"
        val p2Text: TextView = findViewById (R.id.player2)
        p2Text.text = "$player2Score"

        // ゲーム終了なら
        val result1Text: TextView = findViewById (R.id.player1result)
        val result2Text: TextView = findViewById (R.id.player2result)

        if (nowTurnCount > totalTurn){
            // Turn表示ではなくGameset と表示する
            turnText.text = "GameSet!" +
                    "Card ${deckField1.stack.size + deckField1.handCard.size} / ${deckField1.deck.size}"
            if (player1Score > player2Score){
                result1Text.text = "WIN!!"
                result2Text.text = "LOSE..."
            }
            else if (player1Score < player2Score){
                result1Text.text = "LOSE..."
                result2Text.text = "WIN!!"
            }
            else{
                result1Text.text = "DRAW"
                result2Text.text = "DRAW"
            }
        }
        nowTurnCount++
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


    // プレビューを表示する
    private fun preview(){
        // カードの範囲から座標にまず変換する
        val putCoordinates = mutableListOf<IntArray>()
        for (i in 0 until selectedCardRange.size){
            for (j in 0 until selectedCardRange[0].size){
                if (selectedCardRange[i][j] == 1){

                    val x = selectedGridCoordinates[0] + (i -2)
                    val y = selectedGridCoordinates[1] + (j -2)
                    // 範囲外なら何もしない
                    if ((x < 0) || (x >= gridmap.gridmap.size)){
                        continue
                    }
                    if ((y < 0) || (y >= gridmap.gridmap[0].size)){
                        continue
                    }

                    putCoordinates.add(intArrayOf(x, y))
                }
            }
        }

        for ((x, y) in putCoordinates){
            println(x.toString() + y.toString())
        }

        for (i in 0 until gridmap.gridmap.size){
            for (j in 0 until gridmap.gridmap[0].size){

                val imageButtonId = indexToId(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)

                if (gridmap.gridmap[i][j] == Condition.Player1) {
                    myImage.setBackgroundResource(R.drawable.blue)
                    player1Score++
                } else if (gridmap.gridmap[i][j] == Condition.Player2) {
                    myImage.setBackgroundResource(R.drawable.yellow)
                    player2Score++
                } else {
                    myImage.setBackgroundResource(R.drawable.gray)
                }
            }
        }
        for ((i, j) in putCoordinates){

            val imageButtonId = indexToId(i, j)
            val myImage: ImageButton = findViewById (imageButtonId)

            // 置けるのであれば水色
            if (gridmap.gridmap[i][j] == Condition.Empty){
                myImage.setBackgroundResource(R.drawable.tentative_blue)
            }
            //置けないなら灰色
            else{
                myImage.setBackgroundResource(R.drawable.tentative_gray)
            }

        }

    }

    // コンピュータが実行するとき
    private fun computerTurn(): Triple<IntArray, Array<IntArray>, Condition>{
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
                    if (gridmap.canset(intArrayOf(i, j), choiceRange1, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange1)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange2, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange2)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange3, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange3)
                    }
                    if (gridmap.canset(intArrayOf(i, j), choiceRange4, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange4)
                    }
                }
            }
            if (candidates.isNotEmpty()){
                val randomNum = (candidates.indices).random()
                // gridmap.setcolor(candidates[randomNum], ranges[randomNum], Condition.Player2)
                deckField2.choice(choiceCardId)
                return Triple(candidates[randomNum], ranges[randomNum], Condition.Player2)
            }
        }

        // 置けなくなったら適当に選んで捨てる
        for (choiceCardId in 0 until deckField2.handCard.size){
            val choiceCard = deckField2.handCard[choiceCardId]
            if (choiceCard != -1){
                deckField2.choice(choiceCardId)
                return Triple(intArrayOf(0, 0), Array(5){ intArrayOf(0, 0, 0, 0, 0)}, Condition.Player2)
            }
        }

        // たぶんここまでいかない
        return Triple(intArrayOf(0, 0), Array(5){ intArrayOf(0, 0, 0, 0, 0)}, Condition.Player2)
    }

    // 自分と相手のカードから盤面を更新するまでの流れ
    private fun play(){
        val computer = computerTurn()
        var myRangeSize = 0
        var comRangeSize = 0
        // それぞれのカードの範囲を比較
        for (i in 0 until 5){
            for (j in 0 until 5){
                if (selectedCardRange[i][j] == 1){
                    myRangeSize++
                }
                if (computer.second[i][j] == 1){
                    comRangeSize++
                }
            }
        }

        // カードの範囲が大きい順に更新していく
        if (myRangeSize < comRangeSize){
            gridmap.setcolor(computer.first, computer.second, computer.third)
            gridmap.setcolor(selectedGridCoordinates, selectedCardRange, Condition.Player1)
        }
        else if(myRangeSize > comRangeSize){
            gridmap.setcolor(selectedGridCoordinates, selectedCardRange, Condition.Player1)
            gridmap.setcolor(computer.first, computer.second, computer.third)
        }
        else{
            // 同じならランダムで更新
            val rv = Math.random()
            if (100 * rv > 50){
                gridmap.setcolor(computer.first, computer.second, computer.third)
                gridmap.setcolor(selectedGridCoordinates, selectedCardRange, Condition.Player1)
            }
            else{
                gridmap.setcolor(selectedGridCoordinates, selectedCardRange, Condition.Player1)
                gridmap.setcolor(computer.first, computer.second, computer.third)
            }
        }


        cardflag = false
        gridflag = false
        deckField1.choice(selectedCardId)
        setCard(deckField1.imageArray())
        updategrid(gridmap.gridmap)
        selectedGridCoordinates = intArrayOf(6, 4)
    }
}