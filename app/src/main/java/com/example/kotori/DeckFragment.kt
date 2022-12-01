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

        // バトル画面への遷移
        binding.editButton.setOnClickListener{
            // デッキが選択されていない場合には動作しないようにする
            // デッキの番号をFragmentBattle に渡す
            if (selectDeckFlag){
                val action = DeckFragmentDirections
                    .actionDeckFragmentToSelectFragment(selectDeckNumber.toString())
                findNavController().navigate(action)
            }
        }

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
                it.setOnClickListener { onClickDeckButton(i+1) }
            }
            binding.deckButtonGrid.addView(button)
        }


        // デッキ画像の生成
        val deckImageColumn = 2
        val deckImageRow = 4
        for (i in 0 until deckImageColumn * deckImageRow){
            // GridLayoutを使用するので、rowとcolumnを指定
            val dp = resources.displayMetrics.density
            val params = GridLayout.LayoutParams().also {
                it.rowSpec = GridLayout.spec(i / deckImageRow)
                it.columnSpec = GridLayout.spec(i % deckImageRow)
                it.width = (95 * dp).roundToInt()
                it.height = (95 * dp).roundToInt()
            }
            val imageView = ImageView(view.context).also {
                it.layoutParams = params
                it.setBackgroundResource(R.drawable.empty)
            }
            binding.cardViewGrid.addView(imageView)
        }

        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // デッキボタンを押したときに実行する
    private fun onClickDeckButton(clickDeck: Int){

        // クリックしたデッキの番号を記憶しておく
        val internal = requireContext().filesDir
        val file = File(internal, "data$clickDeck")

        // ファイルが無ければ作成する
        if (!file.exists()){
            val bufferedWriter = file.bufferedWriter()
            val fileContent = "0\n1\n2\n3\n4\n5\n6\n7"
            bufferedWriter.write(fileContent)
            bufferedWriter.close()
            println("Create New Deck")
        }

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()
        var cardIndex = 0

        // 動的な実装を行う
        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            val v = binding.cardViewGrid.getChildAt(cardIndex)
            v.setBackgroundResource(AllCard[cardId].Image)
            cardIndex++
        }

        selectDeckFlag = true
        selectDeckNumber = clickDeck
        return
    }

}