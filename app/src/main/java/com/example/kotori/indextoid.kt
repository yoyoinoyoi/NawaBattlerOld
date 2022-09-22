package com.example.kotori

import android.view.View
import com.example.kotori.R

fun indextoid (x: Int, y:Int): Int {
    /**
     算出した座標からImageButton-idに対応させる
     */

    // 100 > arrsize
    val index = 100 * x +y
    val ider = when(index){
//        intArrayOf(0, 0) -> R.id.P00
//        intArrayOf(0, 1) -> R.id.P01
//        intArrayOf(0, 2) -> R.id.P02
//        intArrayOf(0, 3) -> R.id.P03
//        intArrayOf(0, 4) -> R.id.P04
//        intArrayOf(1, 0) -> R.id.P10
//        intArrayOf(1, 1) -> R.id.P11
//        intArrayOf(1, 2) -> R.id.P12
//        intArrayOf(1, 3) -> R.id.P13
//        intArrayOf(1, 4) -> R.id.P14
//        intArrayOf(2, 0) -> R.id.P20
//        intArrayOf(2, 1) -> R.id.P21
//        intArrayOf(2, 2) -> R.id.P22
//        intArrayOf(2, 3) -> R.id.P23
//        intArrayOf(2, 4) -> R.id.P24
//        intArrayOf(3, 0) -> R.id.P30
//        intArrayOf(3, 1) -> R.id.P31
//        intArrayOf(3, 2) -> R.id.P32
//        intArrayOf(3, 3) -> R.id.P33
//        intArrayOf(3, 4) -> R.id.P34
//        intArrayOf(4, 0) -> R.id.P40
//        intArrayOf(4, 1) -> R.id.P41
//        intArrayOf(4, 2) -> R.id.P42
//        intArrayOf(4, 3) -> R.id.P43
//        intArrayOf(4, 4) -> R.id.P44
//        intArrayOf(5, 0) -> R.id.P50
//        intArrayOf(5, 1) -> R.id.P51
//        intArrayOf(5, 2) -> R.id.P52
//        intArrayOf(5, 3) -> R.id.P53
//        intArrayOf(5, 4) -> R.id.P54
        0 -> R.id.P00
        1 -> R.id.P01
        2 -> R.id.P02
        3 -> R.id.P03
        4 -> R.id.P04
        100 -> R.id.P10
        101 -> R.id.P11
        102 -> R.id.P12
        103 -> R.id.P13
        104 -> R.id.P14
        200 -> R.id.P20
        201 -> R.id.P21
        202 -> R.id.P22
        203 -> R.id.P23
        204 -> R.id.P24
        300 -> R.id.P30
        301 -> R.id.P31
        302 -> R.id.P32
        303 -> R.id.P33
        304 -> R.id.P34
        400 -> R.id.P40
        401 -> R.id.P41
        402 -> R.id.P42
        403 -> R.id.P43
        404 -> R.id.P44
        500 -> R.id.P50
        501 -> R.id.P51
        502 -> R.id.P52
        503 -> R.id.P53
        504 -> R.id.P54
        else -> R.id.button3
    }
    return  ider
}