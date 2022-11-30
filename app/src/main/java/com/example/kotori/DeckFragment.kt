package com.example.kotori

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotori.data.AllCard
import com.example.kotori.databinding.FragmentDeckBinding
import java.io.*
import kotlin.math.roundToInt

class DeckFragment : Fragment() {

    // クリックしたデッキ番号
    private var clickDeck = -1

    private var _binding: FragmentDeckBinding? = null
    private val binding get() = _binding!!

    private var selectDeckNumber = 1
    private var selectDeckFlag = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeckBinding.inflate(inflater, container, false)
        val view = binding.root

        /**
         * 各ボタンごとにクリックイベントを設定
         */
//
//        // バトル画面への遷移
//        binding.battleButton.setOnClickListener{
//            // デッキが選択されていない場合には動作しないようにする
//            // デッキの番号をFragmentBattle に渡す
//            if (selectDeckFlag){
//                val action = LobbyFragmentDirections
//                    .actionLobbyFragmentToBattleFragment(selectDeckNumber.toString())
//                findNavController().navigate(action)
//            }
//        }

        // デッキ選択ボタン群の生成
        val column = 4
        val row = 2
        for (i in 0 until column * row){
            // GridLayoutを使用するので、rowとcolumnを指定
            val dp = resources.displayMetrics.density
            val params = GridLayout.LayoutParams().also {
                it.rowSpec = GridLayout.spec(i / row)
                it.columnSpec = GridLayout.spec(i % row)
                it.width = (120 * dp).roundToInt()
                it.height = (80 * dp).roundToInt()
            }
            val button = Button(view.context).also {
                it.text = "No$i"
                it.layoutParams = params
            }
            binding.deckButtonGrid.addView(button)
        }
//
//        // そのボタンにクリックイベントを付与する
//        for (i in 0 until binding.deckButtonGrid.childCount) {
//            val v = binding.deckButtonGrid.getChildAt(i)
//            v.setOnClickListener { onClickDeckButton(i+1) }
//        }

        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    // デッキボタンを押したときに実行する
//    private fun onClickDeckButton(clickDeck: Int){
//
//        // クリックしたデッキの番号を記憶しておく
//        val internal = requireContext().filesDir
//        val file = File(internal, "data$clickDeck")
//
//        // ファイルが無ければ作成する
//        if (!file.exists()){
//            val bufferedWriter = file.bufferedWriter()
//            val fileContent = "0\n1\n2\n3\n4\n5\n6\n7"
//            bufferedWriter.write(fileContent)
//            bufferedWriter.close()
//            println("Create New Deck")
//        }
//
//        // ファイルにかかれたカードの画像を表示する
//        val bufferedReader = file.bufferedReader()
//        var cardIndex = 0
//
//        val column = 2
//        val row = 4
//
//        // いったん表示カードを削除する
//        binding.cardViewGrid.removeAllViews()
//
//        // 動的な実装を行う
//        bufferedReader.readLines().forEach {
//            val cardId = it.toInt()
//            // GridLayoutを使用するので、rowとcolumnを指定
//            val dp = resources.displayMetrics.density
//            val params = GridLayout.LayoutParams().also { params_it ->
//                params_it.rowSpec = GridLayout.spec(cardIndex / row)
//                params_it.columnSpec = GridLayout.spec(cardIndex % row)
//                params_it.width = (95 * dp).roundToInt()
//                params_it.height = (95 * dp).roundToInt()
//            }
//            val imageView = ImageView(requireContext()).also { image_it ->
//                image_it.layoutParams = params
//                image_it.setBackgroundResource(AllCard[cardId].Image)
//            }
//            binding.cardViewGrid.addView(imageView)
//            cardIndex++
//        }
//
//        selectDeckFlag = true
//        selectDeckNumber = clickDeck
//        return
//    }
//
//    // 戻るボタンをクリックしたときの処理
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            // デッキ編集画面に戻る
//            val intent = Intent(this, MainFragment::class.java)
//            startActivity(intent)
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    /*
//    * デッキをクリックしたときに実行される関数
//     */
//    fun onClickDeck(view: View){
//
//        // クリックしたデッキの番号を記憶しておく
//        clickDeck = convertIdToDeck(view)
//
//        val internal = applicationContext.filesDir
//        val file = File(internal, "data$clickDeck")
//
//        // ファイルが無ければ作成する
//        if (!file.exists()){
//            val bufferedWriter = file.bufferedWriter()
//            bufferedWriter.write("0\n1\n2\n3\n4\n5\n6\n7")
//            bufferedWriter.close()
//            println("Create Deck")
//        }
//
//        // ファイルにかかれたカードの画像を画面下に表示する
//        val bufferedReader = file.bufferedReader()
//        var cardIndex = 0
//
//        bufferedReader.readLines().forEach {
//            val cardId = it.toInt()
//            val myImage: ImageButton = findViewById(convertDeckToId(cardIndex))
//            myImage.setBackgroundResource(AllCard[cardId].Image)
//            cardIndex++
//        }
//    }
//
//    fun onClickEdit(view: View){
//        // クリックしたらclickDeck に従ってデッキ編集画面に遷移
//        if (clickDeck == -1){
//            return
//        }
////        val intent = Intent(this, SelectActivity::class.java)
////        intent.putExtra("deckId",clickDeck.toString())
////        startActivity(intent)
//    }
//
//    fun onClickRename(view: View){
//        return
//    }
//
//    private fun convertDeckToId(index: Int): Int{
//        val button = when(index) {
//            0 -> R.id.deckCard1
//            1 -> R.id.deckCard2
//            2 -> R.id.deckCard3
//            3 -> R.id.deckCard4
//            4 -> R.id.deckCard5
//            5 -> R.id.deckCard6
//            6 -> R.id.deckCard7
//            7 -> R.id.deckCard8
//            else -> R.id.deckCard1
//        }
//        return button
//    }

}