package com.example.kotori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotori.data.AllCard

class MyAdapter(private val iImages: ArrayList<Int>):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    lateinit var listener: OnItemClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageButton: ImageButton

        init {
            // Define click listener for the ViewHolder's View.
            imageButton = view.findViewById(R.id.image_view)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.my_text_view, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.imageButton.setBackgroundResource(iImages[position])
        viewHolder.imageButton.setOnClickListener {
            listener.onItemClickListener(it, position, AllCard[position].Image.toString())
        }
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = iImages.size
}