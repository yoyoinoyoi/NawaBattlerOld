package com.example.kotori

import android.view.View
import com.example.kotori.R

fun indextoid (x: Int, y:Int): Int {
    /**
     算出した座標からImageButton-idに対応させる
     */

    // 100 > arrSize
    val index = 100 * x +y
    val ider = when(index){
        0 -> R.id.P0000
        1 -> R.id.P0001
        2 -> R.id.P0002
        3 -> R.id.P0003
        4 -> R.id.P0004
        5 -> R.id.P0005
        6 -> R.id.P0006
        7 -> R.id.P0007
        8 -> R.id.P0008
        9 -> R.id.P0009
        100 -> R.id.P0100
        101 -> R.id.P0101
        102 -> R.id.P0102
        103 -> R.id.P0103
        104 -> R.id.P0104
        105 -> R.id.P0105
        106 -> R.id.P0106
        107 -> R.id.P0107
        108 -> R.id.P0108
        109 -> R.id.P0109
        200 -> R.id.P0200
        201 -> R.id.P0201
        202 -> R.id.P0202
        203 -> R.id.P0203
        204 -> R.id.P0204
        205 -> R.id.P0205
        206 -> R.id.P0206
        207 -> R.id.P0207
        208 -> R.id.P0208
        209 -> R.id.P0209
        300 -> R.id.P0300
        301 -> R.id.P0301
        302 -> R.id.P0302
        303 -> R.id.P0303
        304 -> R.id.P0304
        305 -> R.id.P0305
        306 -> R.id.P0306
        307 -> R.id.P0307
        308 -> R.id.P0308
        309 -> R.id.P0309
        400 -> R.id.P0400
        401 -> R.id.P0401
        402 -> R.id.P0402
        403 -> R.id.P0403
        404 -> R.id.P0404
        405 -> R.id.P0405
        406 -> R.id.P0406
        407 -> R.id.P0407
        408 -> R.id.P0408
        409 -> R.id.P0409
        500 -> R.id.P0500
        501 -> R.id.P0501
        502 -> R.id.P0502
        503 -> R.id.P0503
        504 -> R.id.P0504
        505 -> R.id.P0505
        506 -> R.id.P0506
        507 -> R.id.P0507
        508 -> R.id.P0508
        509 -> R.id.P0509
        600 -> R.id.P0600
        601 -> R.id.P0601
        602 -> R.id.P0602
        603 -> R.id.P0603
        604 -> R.id.P0604
        605 -> R.id.P0605
        606 -> R.id.P0606
        607 -> R.id.P0607
        608 -> R.id.P0608
        609 -> R.id.P0609
        700 -> R.id.P0700
        701 -> R.id.P0701
        702 -> R.id.P0702
        703 -> R.id.P0703
        704 -> R.id.P0704
        705 -> R.id.P0705
        706 -> R.id.P0706
        707 -> R.id.P0707
        708 -> R.id.P0708
        709 -> R.id.P0709
        800 -> R.id.P0800
        801 -> R.id.P0801
        802 -> R.id.P0802
        803 -> R.id.P0803
        804 -> R.id.P0804
        805 -> R.id.P0805
        806 -> R.id.P0806
        807 -> R.id.P0807
        808 -> R.id.P0808
        809 -> R.id.P0809
        900 -> R.id.P0900
        901 -> R.id.P0901
        902 -> R.id.P0902
        903 -> R.id.P0903
        904 -> R.id.P0904
        905 -> R.id.P0905
        906 -> R.id.P0906
        907 -> R.id.P0907
        908 -> R.id.P0908
        909 -> R.id.P0909
        1000 -> R.id.P1000
        1001 -> R.id.P1001
        1002 -> R.id.P1002
        1003 -> R.id.P1003
        1004 -> R.id.P1004
        1005 -> R.id.P1005
        1006 -> R.id.P1006
        1007 -> R.id.P1007
        1008 -> R.id.P1008
        1009 -> R.id.P1009
        1100 -> R.id.P1100
        1101 -> R.id.P1101
        1102 -> R.id.P1102
        1103 -> R.id.P1103
        1104 -> R.id.P1104
        1105 -> R.id.P1105
        1106 -> R.id.P1106
        1107 -> R.id.P1107
        1108 -> R.id.P1108
        1109 -> R.id.P1109
        else -> R.id.cardbutton3
    }
    return  ider
}