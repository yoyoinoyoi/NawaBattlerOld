package com.example.kotori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.kotori.data.AllCard

class MyAdapter(private val iImages: ArrayList<Int>):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageButton: ImageButton

        init {
            imageButton = view.findViewById(R.id.image_view)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.imageButton.setBackgroundResource(iImages[position])
        viewHolder.imageButton.setOnClickListener {
            listener.onItemClickListener(it, position, AllCard[position].Image.toString())
        }
    }

    // インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    // サイズを返す関数
    override fun getItemCount() = iImages.size
}