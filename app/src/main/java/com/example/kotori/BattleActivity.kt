package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class BattleActivity : AppCompatActivity() {
    data class Card(val Image: String, val Range: Array<IntArray>)

    // グリッド情報
    public var gridmap = Array(6){ Array(5){condition.Empty} }
    // グリッドで選択されたマスの座標. (-1, -1) は初期値
    public var selectedCoordinate = intArrayOf(-1, -1)
    // デッキ
    // public var Deck = Card("@drawable/dandadan", )
    // どちらのターンかを識別する
    public var nowTurn = condition.Player1
    // 選択されたカードの識別番号
    public var selectedCardId = -1
    // 現在グリッドを操作しているか
    public var gridflag = false
    // 現在カードを操作しているか
    public  var cardflag = false
    // 選択されたカードの効果
    public var selectedCardStatus = Array(5){ intArrayOf(0, 0, 0, 0, 0) }

    public var click = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_field)
//
//        val targetName = "@drawable/dandadan"
//        val c1 = intArrayOf(1, 0, 1, 0, 1)
//        val c2 = intArrayOf(1, 1, 0, 0, 0)
//        val c3 = intArrayOf(1, 0, 0, 0, 0)
//        val c4 = intArrayOf(0, 0, 0, 0, 1)
//        val c5 = intArrayOf(0, 1, 0, 0, 0)
//        val gridmap = Array(5) { IntArray(5) }
//        gridmap.set(0, c1)
//        gridmap.set(1, c2)
//        gridmap.set(2, c3)
//        gridmap.set(3, c4)
//        gridmap.set(4, c5)
//
          }

    // グリッドをクリックしたときに実行される関数
    @SuppressLint("SetTextI18n")
    fun onClickGrid(view: View){

        click++
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

        // 確認用
        val clickText: TextView = findViewById(R.id.data)
        clickText.text = index[0].toString() + index[1].toString() + gridmap[index[0]][index[1]].toString() + click.toString()


        // 一致していなければ選択を更新し、プレビューを表示

        // フロントの更新
        gridmap[index[0]][index[1]] = nowTurn
        updategrid()

        // 確認用
        val clickTextafter: TextView = findViewById(R.id.dataafter)
        clickTextafter.text = index[0].toString() + index[1].toString() + gridmap[index[0]][index[1]].toString() + click.toString()


        if (nowTurn === condition.Player1){
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
    private fun updategrid () {
        val gridcolumn = gridmap.size
        val gridrow = gridmap[0].size
        for (i in 0 until gridcolumn) {
            for (j in 0 until gridrow) {
                // val arr = intArrayOf(i, j)
                val imageButtonId = indextoid(i, j)
                val myImage: ImageButton = findViewById (imageButtonId)
                // println(i.toString() +j.toString() +imageButtonId)
                when (gridmap[i][j]) {
                    condition.Empty -> myImage.setBackgroundResource(R.drawable.gray)
                    condition.Player1 -> myImage.setBackgroundResource(R.drawable.blue)
                    condition.Player2 -> myImage.setBackgroundResource(R.drawable.yellow)
                }
            }
        }
    }


}