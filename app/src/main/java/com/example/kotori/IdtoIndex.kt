package com.example.kotori

import android.view.View
import com.example.kotori.R

// ImageButton の位置から座標を返す関数
fun idtoindex(view: View): IntArray{

    var index = when(view.id){
        R.id.P00 -> intArrayOf(0, 0)
        R.id.P01 -> intArrayOf(0, 1)
        R.id.P02 -> intArrayOf(0, 2)
        R.id.P03 -> intArrayOf(0, 3)
        R.id.P04 -> intArrayOf(0, 4)
        R.id.P10 -> intArrayOf(1, 0)
        R.id.P11 -> intArrayOf(1, 1)
        R.id.P12 -> intArrayOf(1, 2)
        R.id.P13 -> intArrayOf(1, 3)
        R.id.P14 -> intArrayOf(1, 4)
        R.id.P20 -> intArrayOf(2, 0)
        R.id.P21 -> intArrayOf(2, 1)
        R.id.P22 -> intArrayOf(2, 2)
        R.id.P23 -> intArrayOf(2, 3)
        R.id.P24 -> intArrayOf(2, 4)
        R.id.P30 -> intArrayOf(3, 0)
        R.id.P31 -> intArrayOf(3, 1)
        R.id.P32 -> intArrayOf(3, 2)
        R.id.P33 -> intArrayOf(3, 3)
        R.id.P34 -> intArrayOf(3, 4)
        R.id.P40 -> intArrayOf(4, 0)
        R.id.P41 -> intArrayOf(4, 1)
        R.id.P42 -> intArrayOf(4, 2)
        R.id.P43 -> intArrayOf(4, 3)
        R.id.P44 -> intArrayOf(4, 4)
        R.id.P50 -> intArrayOf(5, 0)
        R.id.P51 -> intArrayOf(5, 1)
        R.id.P52 -> intArrayOf(5, 2)
        R.id.P53 -> intArrayOf(5, 3)
        R.id.P54 -> intArrayOf(5, 4)
        else -> intArrayOf(100, 100)
    }
    return index
}