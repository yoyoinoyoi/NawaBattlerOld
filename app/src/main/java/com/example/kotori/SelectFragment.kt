package com.example.kotori

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.*
import com.example.kotori.data.AllCard
import com.example.kotori.databinding.FragmentSelectBinding
import java.io.File
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class SelectFragment : Fragment() {

    // 暫定のデッキ内容
    private val tmpDeck = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0)

    // 選択されたカードid
    var selectAllCard = -1

    // 選択されたデッキのカード
    var selectDeckCard = -1

    // カード選択フラグ
    var cardFlag = false

    private val args: SelectFragmentArgs by navArgs()

    private var _binding: FragmentSelectBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectBinding.inflate(inflater, container, false)
        val view = binding.root

        // 1次元のリストを作成
        val layoutManager = LinearLayoutManager(requireContext())
        // リストに区切り線を追加
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)

        val images: ArrayList<Int> = arrayListOf()
        for (element in AllCard) {
            images.add(element.Image)
        }

        val adapter = MyAdapter(images)

        // バインディングに適用
        binding.cardRecyclerView.layoutManager = layoutManager
        binding.cardRecyclerView.addItemDecoration(dividerItemDecoration)
        binding.cardRecyclerView.adapter = adapter

        /**
         * 各ボタンごとにクリックイベントを設定
         */

        binding.OKButton.setOnClickListener {
            onClickOK()
            val action = SelectFragmentDirections.actionSelectFragmentToDeckFragment()
            findNavController().navigate(action)
        }
        binding.selectCardImage.setBackgroundResource(R.drawable.empty)

        // 選んだデッキから生成する
        // クリックされたデッキのカードを下に表示する
        val deckId = args.selectDeckNumber
        val internal = requireContext().filesDir
        val file = File(internal, "data$deckId")

        // ファイルにかかれたカードの画像を表示する
        val bufferedReader = file.bufferedReader()
        var t = 0

        bufferedReader.readLines().forEach {
            val cardId = it.toInt()
            tmpDeck[t] = cardId
            t++
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
            val imageButton = ImageButton(view.context).also {
                it.layoutParams = params
                it.setBackgroundResource(AllCard[tmpDeck[i]].Image)
                it.setOnClickListener { onClickDeckCard(i) }
            }
            binding.cardViewGrid.addView(imageButton)
        }

        // 上の画面のカードをクリックしたときに実行する関数
        adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClickListener(view: View, position: Int, clickedText: String) {
                onClickAllCard(position)
            }
        })
        
        return view
    }

    // 終わったら破棄を忘れない
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 変更を確定
    private fun onClickOK() {

        val deckId = args.selectDeckNumber
        val internal = requireContext().filesDir
        val file = File(internal, "data$deckId")

        val bufferedWriter = file.bufferedWriter()
        tmpDeck.forEach(){
            println(it.toString())
            bufferedWriter.write(it.toString())
            bufferedWriter.newLine()
        }
        bufferedWriter.close()
    }

    // 画面下のカードをクリックしたときに実行される関数
    private fun onClickDeckCard(cardId : Int) {
        if (!cardFlag) {
            return
        }

        val v = binding.cardViewGrid.getChildAt(cardId)
        v.setBackgroundResource(AllCard[selectAllCard].Image)
        binding.selectCardImage.setBackgroundResource(R.drawable.empty)
        tmpDeck[cardId] = selectAllCard
        selectDeckCard = cardId
        cardFlag = false
        return
    }

    private fun onClickAllCard(cardId: Int) {
        selectAllCard = cardId
        cardFlag = true
        binding.selectCardImage.setBackgroundResource(AllCard[selectAllCard].Image)
    }

}
