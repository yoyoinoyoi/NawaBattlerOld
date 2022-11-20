package com.example.kotori

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.kotori.data.AllCard
import com.example.kotori.databinding.FragmentLobbyBinding
import com.example.kotori.method.convertDeckToId
import com.example.kotori.method.convertIdToDeck
import java.io.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LobbyFragment : Fragment() {

    private var _binding: FragmentLobbyBinding? = null
    private val binding get() = _binding!!

    private var selectDeckNumber = 0
    private var selectDeckFlag = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLobbyBinding.inflate(inflater, container, false)
        val view = binding.root

        /**
         * 各ボタンごとにクリックイベントを設定
        */

        // バトル画面への遷移
        binding.battleButton.setOnClickListener{
            // デッキが選択されていない場合には動作しないようにする
            // デッキの番号をFragmentBattle に渡す
            if (selectDeckFlag){
                val action = LobbyFragmentDirections.actionLobbyFragmentToBattleFragment()
                findNavController().navigate(action)
            }
        }

        // デッキボタンの設定
        binding.deckButton1.setOnClickListener { func(it) }
        binding.deckButton2.setOnClickListener { func(it) }
        binding.deckButton3.setOnClickListener { func(it) }
        binding.deckButton4.setOnClickListener { func(it) }
        binding.deckButton5.setOnClickListener { func(it) }
        binding.deckButton6.setOnClickListener { func(it) }
        binding.deckButton7.setOnClickListener { func(it) }
        binding.deckButton8.setOnClickListener { func(it) }

        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // クリックイベントの関数
    private fun func(view: View){

        // クリックしたデッキの番号を記憶しておく
        val clickDeck = convertIdToDeck(view)
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
            val myImage: ImageView = binding.root.findViewById(convertDeckToId(cardIndex))
            myImage.setBackgroundResource(AllCard[cardId].Image)
            cardIndex++
        }

        selectDeckFlag = true
        selectDeckNumber = clickDeck
        return
    }

}
