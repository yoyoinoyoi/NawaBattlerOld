package com.example.kotori

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout

class SubActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val row = LinearLayout(this)
        val column = LinearLayout(this)

        row.orientation = LinearLayout.VERTICAL
        column.orientation = LinearLayout.HORIZONTAL

        row.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        column.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val imageButton = Array(8){ arrayOfNulls<ImageButton?>(8) }
        //val imageButton = arrayOf<Array<ImageButton>>()

        for (i in 0..7)
            for (j in 0..7)
                imageButton[i][j] = ImageButton(this)


        for (i in 0..7) {
            for (j in 0..7) {
                imageButton[i][j]!!.setImageResource(R.drawable.gray)
                imageButton[i][j]!!.layoutParams = LinearLayout.LayoutParams(35, 35)
                if ((j + 1) % 2 != 0) {
                    if ((i + 1) % 2 != 0)
                        imageButton[i][j]!!.setBackgroundColor(R.color.blue)
                    else
                        imageButton[i][j]!!.setBackgroundColor(R.color.yellow)
                } else {
                    if ((i + 1) % 2 != 0)
                        imageButton[i][j]!!.setBackgroundColor(R.color.yellow)
                    else
                        imageButton[i][j]!!.setBackgroundColor(R.color.blue)
                }
            }
        }
    }
}