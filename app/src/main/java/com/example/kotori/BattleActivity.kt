package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.kotori.structure.DeckManager
import com.example.kotori.structure.FieldManager
import com.example.kotori.structure.Condition
import com.example.kotori.data.AllCard
import com.example.kotori.method.convertCoordinateToId
import com.example.kotori.method.convertIdToCoordinate
import java.io.File

class BattleActivity : AppCompatActivity() {

    // 現在グリッドを操作しているか
    var fieldFlag = false
    // 現在カードを操作しているか
    var cardFlag = false
    // 選択されたカードの識別番号
    var selectCardId = -1
    // 選択されたカードの能力(回転させるときなどに一時的に保持するため)
    var selectCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0) }
    // 選択されたgrid の座標
    var selectGridCoordinates = intArrayOf(6, 4)
    // 全ターン数
    private val totalTurn = 5
    // 今のターン数
    var nowTurnCount = 1
    // Player1 のスコア
    var player1Score = 0
    // Player2 のスコア
    var player2Score = 0


    // 初期化

    // グリッド情報
    private var fieldBase = Array(12){ Array(10){ Condition.Empty} }
    private val fieldMain = FieldManager(fieldBase)
    private val deck1 = DeckManager()
    private val deck2 = DeckManager()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        // 上に表示するやつ
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "バトル!!"

        // 戻るボタンをつけるためのもの
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 選んだデッキから生成する

        val deckId = intent.getStringExtra("deckId")!!

        // クリックされたデッキのカードを下に表示する
        val internal = applicationContext.filesDir
        val file = File(internal, "data$deckId")

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            deck1.deck.add(AllCard[cardId])
            deck2.deck.add(AllCard[cardId])
        }
        
        // フロントへ更新
        deck1.deckSetUp()
        deck2.deckSetUp()
        updateField(fieldMain.field)
        setCard(deck1.deckImageList())
    }

    // 戻るボタンをクリックしたときの処理
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // デッキ編集画面に戻る
            val intent = Intent(this, LobbyActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    // グリッドをクリックしたときに実行される関数
    fun onClickGrid(view: View){

        // カードが選択されていなければ何もしない
        if (!cardFlag){
            return
        }

        // クリックしたボタンを座標に変換
        val index = convertIdToCoordinate(view)

        // プレビューを表示する
        if ( !( fieldFlag && (index[0] == selectGridCoordinates[0]) && (index[1] == selectGridCoordinates[1]) ) ){

            selectGridCoordinates = index
            fieldFlag = true
            preview()
            return
        }

        // 置ければ置く
        if ( fieldMain.canSet(index, selectCardRange, Condition.Player1) ){
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
        if (deck1.handCard[clickButton] == -1){
            cardFlag = false
            selectCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
            preview()
            return
        }

        // 同じカードを連続でクリックした場合にはキャンセルする
        if (cardFlag && (clickButton == selectCardId)){
            cardFlag = false
            selectCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
            preview()
            return
        }

        selectCardId = clickButton
        selectCardRange = deck1.deck[deck1.handCard[selectCardId]].Range
        cardFlag = true
        fieldFlag = false
        preview()

    }

    // 回転ボタンをクリックしたときに実行される関数
    fun onClickRotate(view: View){
        // カードが選択されなければ実行しない
        if (!cardFlag) {
            return
        }
        selectCardRange = rotateRange(selectCardRange)
        preview()
    }

    // パスボタンをクリックしたときに実行される関数
    fun onClickPass(view: View){
        if (!cardFlag) {
            return
        }
        selectGridCoordinates = intArrayOf(0, 0)
        selectCardRange = Array(5){ intArrayOf(0, 0, 0, 0, 0)}
        play()
    }

    /* 以下、プライベート関数 */

    // fieldMain からフロントへの更新を行う
    @SuppressLint("SetTextI18n")
    private fun updateField (field: Array<Array<Condition>>) {
        player1Score = 0
        player2Score = 0
        for (i in field.indices) {
            for (j in 0 until field[0].size) {
                val imageButtonId = convertCoordinateToId(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                
                // field の状態によって色を更新する
                if (field[i][j] == Condition.Player1){
                    myImage.setBackgroundResource(R.drawable.blue)
                    player1Score++
                }
                else if (field[i][j] == Condition.Player2){
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

        val scoreText: TextView = findViewById (R.id.score)
        scoreText.text = "$player1Score vs $player2Score"

        // 通常のTurn の更新
        if (nowTurnCount < totalTurn){
            turnText.text = "Turn $nowTurnCount / $totalTurn"
        }
        // ゲーム終了なら
        else if (nowTurnCount > totalTurn){
            if (player1Score > player2Score){
                turnText.text = "WIN!!"
            }
            else if (player1Score < player2Score){
                turnText.text = "LOSE..."
            }
            else{
                turnText.text = "DRAW"
            }
        }
        else{
            turnText.text = "Final Turn!"
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
    private fun rotateRange(range: Array<IntArray>): Array<IntArray>{
        val newList = Array(5){ intArrayOf(0, 0, 0, 0, 0) }
        for (i in range.indices){
            for (j in 0 until range[0].size){
                newList[i][j] = range[j][range[0].size -i -1]
            }
        }
        return newList
    }


    // プレビューを表示する
    private fun preview(){
        for (i in 0 until fieldMain.field.size){
            for (j in 0 until fieldMain.field[0].size){
                val imageButtonId = convertCoordinateToId(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)

                if (fieldMain.field[i][j] == Condition.Player1) {
                    myImage.setBackgroundResource(R.drawable.blue)
                    player1Score++
                } else if (fieldMain.field[i][j] == Condition.Player2) {
                    myImage.setBackgroundResource(R.drawable.yellow)
                    player2Score++
                } else {
                    myImage.setBackgroundResource(R.drawable.gray)
                }
            }
        }

        // 中央部をまず表示(中央部がかぶっている処理は後ろで行う)
        val coreImage: ImageButton = findViewById (convertCoordinateToId(selectGridCoordinates[0], selectGridCoordinates[1]))
        coreImage.setBackgroundResource(R.drawable.tentative_core)

        // カードの範囲から座標にまず変換する
        for (i in selectCardRange.indices){
            for (j in 0 until selectCardRange[0].size){
                if (selectCardRange[i][j] == 1){

                    val x = selectGridCoordinates[0] + (i -2)
                    val y = selectGridCoordinates[1] + (j -2)
                    // 範囲外なら何もしない
                    if ((x < 0) || (x >= fieldMain.field.size)){
                        continue
                    }
                    if ((y < 0) || (y >= fieldMain.field[0].size)){
                        continue
                    }

                    val imageButtonId = convertCoordinateToId(x, y)
                    val myImage: ImageButton = findViewById (imageButtonId)

                    if ((i == 2) && (j == 2)){
                        if (fieldMain.field[x][y] == Condition.Empty){
                            myImage.setBackgroundResource(R.drawable.tentative_blue_core)
                        }
                        //置けないなら灰色
                        else{
                            myImage.setBackgroundResource(R.drawable.tentative_gray_core)
                        }
                    }
                    else{

                        // 置けるのであれば水色
                        if (fieldMain.field[x][y] == Condition.Empty) {
                            myImage.setBackgroundResource(R.drawable.tentative_blue)
                        }
                        //置けないなら灰色
                        else {
                            myImage.setBackgroundResource(R.drawable.tentative_gray)
                        }
                    }
                }
            }
        }
    }


    // コンピュータが実行するとき
    private fun computerTurn(): Triple<IntArray, Array<IntArray>, Condition>{
        // deck はdeckField2 を用いる

        // 手札からまずカードを決める
        for (choiceCardId in 0 until deck2.handCard.size){
            // 回転させる
            val choiceCard = deck2.handCard[choiceCardId]
            if (choiceCard == -1){
                continue
            }
            val choiceRange1 = deck2.deck[choiceCard].Range
            val choiceRange2 = rotateRange(choiceRange1)
            val choiceRange3 = rotateRange(choiceRange2)
            val choiceRange4 = rotateRange(choiceRange3)

            val candidates = mutableListOf<IntArray>()
            val ranges = mutableListOf<Array<IntArray>>()
            for (i in 0 until fieldMain.field.size){
                for (j in 0 until fieldMain.field[0].size){
                    if (fieldMain.canSet(intArrayOf(i, j), choiceRange1, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange1)
                    }
                    if (fieldMain.canSet(intArrayOf(i, j), choiceRange2, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange2)
                    }
                    if (fieldMain.canSet(intArrayOf(i, j), choiceRange3, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange3)
                    }
                    if (fieldMain.canSet(intArrayOf(i, j), choiceRange4, Condition.Player2)){
                        candidates.add(intArrayOf(i, j))
                        ranges.add(choiceRange4)
                    }
                }
            }
            if (candidates.isNotEmpty()){
                val randomNum = (candidates.indices).random()
                deck2.deckDraw(choiceCardId)
                return Triple(candidates[randomNum], ranges[randomNum], Condition.Player2)
            }
        }

        // 置けなくなったら適当に選んで捨てる
        for (choiceCardId in 0 until deck2.handCard.size){
            val choiceCard = deck2.handCard[choiceCardId]
            if (choiceCard != -1){
                deck2.deckDraw(choiceCardId)
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
                if (selectCardRange[i][j] == 1){
                    myRangeSize++
                }
                if (computer.second[i][j] == 1){
                    comRangeSize++
                }
            }
        }

        // カードの範囲が大きい順に更新していく
        if (myRangeSize < comRangeSize){
            fieldMain.setColor(computer.first, computer.second, computer.third)
            fieldMain.setColor(selectGridCoordinates, selectCardRange, Condition.Player1)
        }
        else if(myRangeSize > comRangeSize){
            fieldMain.setColor(selectGridCoordinates, selectCardRange, Condition.Player1)
            fieldMain.setColor(computer.first, computer.second, computer.third)
        }
        else{
            // 同じならランダムで更新
            val rv = Math.random()
            if (100 * rv > 50){
                fieldMain.setColor(computer.first, computer.second, computer.third)
                fieldMain.setColor(selectGridCoordinates, selectCardRange, Condition.Player1)
            }
            else{
                fieldMain.setColor(selectGridCoordinates, selectCardRange, Condition.Player1)
                fieldMain.setColor(computer.first, computer.second, computer.third)
            }
        }


        cardFlag = false
        fieldFlag = false
        deck1.deckDraw(selectCardId)
        setCard(deck1.deckImageList())
        updateField(fieldMain.field)
        selectGridCoordinates = intArrayOf(6, 4)
    }
}