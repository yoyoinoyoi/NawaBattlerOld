package com.example.kotori

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.kotori.structure.DeckManager
import com.example.kotori.structure.FieldManager
import com.example.kotori.structure.Condition
import com.example.kotori.data.AllCard
import com.example.kotori.databinding.FragmentBattleBinding
import com.example.kotori.method.convertCoordinateToId
import com.example.kotori.method.convertIdToCoordinate
import java.io.File

class BattleFragment : Fragment() {

    private var _binding: FragmentBattleBinding? = null
    private val binding get() = _binding!!

    private val args: BattleFragmentArgs by navArgs()

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

    private var fieldBase = Array(12){ Array(10){ Condition.Empty} }
    private val fieldMain = FieldManager(fieldBase)
    private val deck1 = DeckManager()
    private val deck2 = DeckManager()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBattleBinding.inflate(inflater, container, false)
        val view = binding.root

        // 選んだデッキから生成する
        // クリックされたデッキのカードを下に表示する
        val deckId = args.selectDeckNumber
        val internal = requireContext().filesDir
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

        /**
         * 各ボタンごとにクリックイベントを設定
         */

        binding.P0000.setOnClickListener{ onClickGrid(it) }
        binding.P0001.setOnClickListener{ onClickGrid(it) }
        binding.P0002.setOnClickListener{ onClickGrid(it) }
        binding.P0003.setOnClickListener{ onClickGrid(it) }
        binding.P0004.setOnClickListener{ onClickGrid(it) }
        binding.P0005.setOnClickListener{ onClickGrid(it) }
        binding.P0006.setOnClickListener{ onClickGrid(it) }
        binding.P0007.setOnClickListener{ onClickGrid(it) }
        binding.P0008.setOnClickListener{ onClickGrid(it) }
        binding.P0009.setOnClickListener{ onClickGrid(it) }
        binding.P0100.setOnClickListener{ onClickGrid(it) }
        binding.P0101.setOnClickListener{ onClickGrid(it) }
        binding.P0102.setOnClickListener{ onClickGrid(it) }
        binding.P0103.setOnClickListener{ onClickGrid(it) }
        binding.P0104.setOnClickListener{ onClickGrid(it) }
        binding.P0105.setOnClickListener{ onClickGrid(it) }
        binding.P0106.setOnClickListener{ onClickGrid(it) }
        binding.P0107.setOnClickListener{ onClickGrid(it) }
        binding.P0108.setOnClickListener{ onClickGrid(it) }
        binding.P0109.setOnClickListener{ onClickGrid(it) }
        binding.P0200.setOnClickListener{ onClickGrid(it) }
        binding.P0201.setOnClickListener{ onClickGrid(it) }
        binding.P0202.setOnClickListener{ onClickGrid(it) }
        binding.P0203.setOnClickListener{ onClickGrid(it) }
        binding.P0204.setOnClickListener{ onClickGrid(it) }
        binding.P0205.setOnClickListener{ onClickGrid(it) }
        binding.P0206.setOnClickListener{ onClickGrid(it) }
        binding.P0207.setOnClickListener{ onClickGrid(it) }
        binding.P0208.setOnClickListener{ onClickGrid(it) }
        binding.P0209.setOnClickListener{ onClickGrid(it) }
        binding.P0300.setOnClickListener{ onClickGrid(it) }
        binding.P0301.setOnClickListener{ onClickGrid(it) }
        binding.P0302.setOnClickListener{ onClickGrid(it) }
        binding.P0303.setOnClickListener{ onClickGrid(it) }
        binding.P0304.setOnClickListener{ onClickGrid(it) }
        binding.P0305.setOnClickListener{ onClickGrid(it) }
        binding.P0306.setOnClickListener{ onClickGrid(it) }
        binding.P0307.setOnClickListener{ onClickGrid(it) }
        binding.P0308.setOnClickListener{ onClickGrid(it) }
        binding.P0309.setOnClickListener{ onClickGrid(it) }
        binding.P0400.setOnClickListener{ onClickGrid(it) }
        binding.P0401.setOnClickListener{ onClickGrid(it) }
        binding.P0402.setOnClickListener{ onClickGrid(it) }
        binding.P0403.setOnClickListener{ onClickGrid(it) }
        binding.P0404.setOnClickListener{ onClickGrid(it) }
        binding.P0405.setOnClickListener{ onClickGrid(it) }
        binding.P0406.setOnClickListener{ onClickGrid(it) }
        binding.P0407.setOnClickListener{ onClickGrid(it) }
        binding.P0408.setOnClickListener{ onClickGrid(it) }
        binding.P0409.setOnClickListener{ onClickGrid(it) }
        binding.P0500.setOnClickListener{ onClickGrid(it) }
        binding.P0501.setOnClickListener{ onClickGrid(it) }
        binding.P0502.setOnClickListener{ onClickGrid(it) }
        binding.P0503.setOnClickListener{ onClickGrid(it) }
        binding.P0504.setOnClickListener{ onClickGrid(it) }
        binding.P0505.setOnClickListener{ onClickGrid(it) }
        binding.P0506.setOnClickListener{ onClickGrid(it) }
        binding.P0507.setOnClickListener{ onClickGrid(it) }
        binding.P0508.setOnClickListener{ onClickGrid(it) }
        binding.P0509.setOnClickListener{ onClickGrid(it) }
        binding.P0600.setOnClickListener{ onClickGrid(it) }
        binding.P0601.setOnClickListener{ onClickGrid(it) }
        binding.P0602.setOnClickListener{ onClickGrid(it) }
        binding.P0603.setOnClickListener{ onClickGrid(it) }
        binding.P0604.setOnClickListener{ onClickGrid(it) }
        binding.P0605.setOnClickListener{ onClickGrid(it) }
        binding.P0606.setOnClickListener{ onClickGrid(it) }
        binding.P0607.setOnClickListener{ onClickGrid(it) }
        binding.P0608.setOnClickListener{ onClickGrid(it) }
        binding.P0609.setOnClickListener{ onClickGrid(it) }
        binding.P0700.setOnClickListener{ onClickGrid(it) }
        binding.P0701.setOnClickListener{ onClickGrid(it) }
        binding.P0702.setOnClickListener{ onClickGrid(it) }
        binding.P0703.setOnClickListener{ onClickGrid(it) }
        binding.P0704.setOnClickListener{ onClickGrid(it) }
        binding.P0705.setOnClickListener{ onClickGrid(it) }
        binding.P0706.setOnClickListener{ onClickGrid(it) }
        binding.P0707.setOnClickListener{ onClickGrid(it) }
        binding.P0708.setOnClickListener{ onClickGrid(it) }
        binding.P0709.setOnClickListener{ onClickGrid(it) }
        binding.P0800.setOnClickListener{ onClickGrid(it) }
        binding.P0801.setOnClickListener{ onClickGrid(it) }
        binding.P0802.setOnClickListener{ onClickGrid(it) }
        binding.P0803.setOnClickListener{ onClickGrid(it) }
        binding.P0804.setOnClickListener{ onClickGrid(it) }
        binding.P0805.setOnClickListener{ onClickGrid(it) }
        binding.P0806.setOnClickListener{ onClickGrid(it) }
        binding.P0807.setOnClickListener{ onClickGrid(it) }
        binding.P0808.setOnClickListener{ onClickGrid(it) }
        binding.P0809.setOnClickListener{ onClickGrid(it) }
        binding.P0900.setOnClickListener{ onClickGrid(it) }
        binding.P0901.setOnClickListener{ onClickGrid(it) }
        binding.P0902.setOnClickListener{ onClickGrid(it) }
        binding.P0903.setOnClickListener{ onClickGrid(it) }
        binding.P0904.setOnClickListener{ onClickGrid(it) }
        binding.P0905.setOnClickListener{ onClickGrid(it) }
        binding.P0906.setOnClickListener{ onClickGrid(it) }
        binding.P0907.setOnClickListener{ onClickGrid(it) }
        binding.P0908.setOnClickListener{ onClickGrid(it) }
        binding.P0909.setOnClickListener{ onClickGrid(it) }
        binding.P1000.setOnClickListener{ onClickGrid(it) }
        binding.P1001.setOnClickListener{ onClickGrid(it) }
        binding.P1002.setOnClickListener{ onClickGrid(it) }
        binding.P1003.setOnClickListener{ onClickGrid(it) }
        binding.P1004.setOnClickListener{ onClickGrid(it) }
        binding.P1005.setOnClickListener{ onClickGrid(it) }
        binding.P1006.setOnClickListener{ onClickGrid(it) }
        binding.P1007.setOnClickListener{ onClickGrid(it) }
        binding.P1008.setOnClickListener{ onClickGrid(it) }
        binding.P1009.setOnClickListener{ onClickGrid(it) }
        binding.P1100.setOnClickListener{ onClickGrid(it) }
        binding.P1101.setOnClickListener{ onClickGrid(it) }
        binding.P1102.setOnClickListener{ onClickGrid(it) }
        binding.P1103.setOnClickListener{ onClickGrid(it) }
        binding.P1104.setOnClickListener{ onClickGrid(it) }
        binding.P1105.setOnClickListener{ onClickGrid(it) }
        binding.P1106.setOnClickListener{ onClickGrid(it) }
        binding.P1107.setOnClickListener{ onClickGrid(it) }
        binding.P1108.setOnClickListener{ onClickGrid(it) }
        binding.P1109.setOnClickListener{ onClickGrid(it) }

        binding.cardbutton1.setOnClickListener { onClickCard(it) }
        binding.cardbutton2.setOnClickListener { onClickCard(it) }
        binding.cardbutton3.setOnClickListener { onClickCard(it) }

        binding.rotatebutton.setOnClickListener { onClickRotate() }
        binding.passbutton.setOnClickListener { onClickPass() }

//        val column = 12
//        val row = 10
//        for (i in 0 until column * row) {
//            // GridLayoutを使用するので、rowとcolumnを指定
//            val params = GridLayout.LayoutParams().also {
//                it.rowSpec = GridLayout.spec(0)
//                it.columnSpec = GridLayout.spec(i)
//            }
//            val button = Button(this).also {
//                it.text = "No$i"
//                it.layoutParams = params
//            }
//            binding.gridLayout2.addView(button)
//            i++
//        }
        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClickGrid(view: View){

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
    private fun onClickCard(view: View){

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
    private fun onClickRotate(){
        // カードが選択されなければ実行しない
        if (!cardFlag) {
            return
        }
        selectCardRange = rotateRange(selectCardRange)
        preview()
    }

    // パスボタンをクリックしたときに実行される関数
    private fun onClickPass(){
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
                val myImage: ImageButton = binding.root.findViewById (imageButtonId)

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
        val turnText: TextView = binding.root.findViewById (R.id.Turn)

        val scoreText: TextView = binding.root.findViewById (R.id.score)
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
        binding.cardbutton1.setBackgroundResource(imageList[0])
        binding.cardbutton2.setBackgroundResource(imageList[1])
        binding.cardbutton3.setBackgroundResource(imageList[2])
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
                val myImage: ImageButton = binding.root.findViewById (imageButtonId)

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
        val coreImage: ImageButton = binding.root.findViewById (convertCoordinateToId(selectGridCoordinates[0], selectGridCoordinates[1]))
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
                    val myImage: ImageButton = binding.root.findViewById (imageButtonId)

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
