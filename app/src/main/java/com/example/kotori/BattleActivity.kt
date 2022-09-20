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
    public var grid = Array(6){ Array(5){condition.Empty} }
    // グリッドで選択されたマスの座標. (-1, -1) は初期値
    public var selectedCoordinate = intArrayOf(-1, -1)
    // デッキ
    // public var Deck = Card("@drawable/dandadan", )
    // どちらのターンかを識別する
    public var nowTurn = condition.Empty
    // 選択されたカードの識別番号
    public var selectedCardId = -1
    // 現在グリッドを操作しているか
    public var gridflag = false
    // 現在カードを操作しているか
    public  var cardflag = false
    // 選択されたカードの効果
    public var selectedCardStatus = Array(5){ intArrayOf(0, 0, 0, 0, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.battle_field)

        val targetName = "@drawable/dandadan"
        val c1 = intArrayOf(1, 0, 1, 0, 1)
        val c2 = intArrayOf(1, 1, 0, 0, 0)
        val c3 = intArrayOf(1, 0, 0, 0, 0)
        val c4 = intArrayOf(0, 0, 0, 0, 1)
        val c5 = intArrayOf(0, 1, 0, 0, 0)
        val grid = Array(5) { IntArray(5) }
        grid.set(0, c1)
        grid.set(1, c2)
        grid.set(2, c3)
        grid.set(3, c4)
        grid.set(4, c5)

        }

    // グリッドをクリックしたときに実行される関数
    @SuppressLint("SetTextI18n")
    fun onClickGrid(view: View){
        // クリックしたボタンを座標に変換
        val index = idtoindex(view)

        // 確認用
        val clickText: TextView = findViewById(R.id.data)
        clickText.text = index[0].toString() + index[1].toString()

        // 座標を保持しておく
        selectedCoordinate = index


        // カードが選択されている場合のみ作動する

        // 今回選択した位置が一致していれば確定する

        // 一致していなければ選択を更新し、プレビューを表示

    }
    // 画面下のカードをクリックしたとき、その情報を引き渡す
    fun onClickCard(view: View?){
        //動作確認
        finish()

        // グリッドを選択していたら解除する

        // カード情報がまだ格納されていないなら、格納する
        //

        // そうでないならカード情報を破棄する(選択解除)
        // 破棄ではなく、cardflag をfalseにする
        cardflag = false
    }

}