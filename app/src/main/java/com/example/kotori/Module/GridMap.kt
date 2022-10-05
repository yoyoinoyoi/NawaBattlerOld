package com.example.kotori.Module

class GridMap(initmap: Array<Array<condition>>) {
    var gridmap: Array<Array<condition>>
    init{
        this.gridmap = initmap
        val gridrow = this.gridmap[0].size
        val gridcolumn = this.gridmap.size
        this.gridmap[gridcolumn-2][1] = condition.Player1
        this.gridmap[1][gridrow-2] = condition.Player2
    }

    // 置くときに含んでないといけないマスの座標を列挙
    private fun limitation(player: condition): MutableSet<Int>{
        val ret = mutableSetOf<Int>()
        // 指定マスの上下左右斜めを確認するためのベクトル
        val vec = arrayOf<IntArray>(
            intArrayOf(1, 0),
            intArrayOf(1, 1),
            intArrayOf(0, 1),
            intArrayOf(-1, 1),
            intArrayOf(-1, 0),
            intArrayOf(-1, -1),
            intArrayOf(0, -1),
            intArrayOf(1, -1))
        // main
        for (i in 0 until this.gridmap.size){
            for (j in 0 until this.gridmap[0].size){
                for ((x, y) in vec){
                    val vx = x + i
                    val vy = y + j
                    // gridmap の外なら除外
                    if ((vx < 0) || (vx >= this.gridmap.size)){
                        continue
                    }
                    if ((vy < 0) || (vy >= this.gridmap[0].size)){
                        continue
                    }
                    if (this.gridmap[vx][vy] !== condition.Empty){
                        continue
                    }

                    // (i, j) がplayer のマスと隣接しているならば候補に追加
                    if (this.gridmap[i][j] == player){
                        ret.add(this.id(intArrayOf(vx, vy)))
                    }
                }
            }
        }
        return ret
    }

    // Range の中心を(0, 0) として返す
    private fun rangetocoordinate(Range: Array<IntArray>): MutableList<IntArray>{
        val ret = mutableListOf<IntArray>()
        for (i in Range.indices){
            for (j in 0 until Range[0].size){
                if (Range[i][j] == 1){
                    ret.add(intArrayOf(i-2, j-2))
                }
            }
        }
        return ret
    }

    // (x, y) の配列をInt 型で返す
    private fun id(coordinate: IntArray): Int{
        return 10000 * coordinate[0] + coordinate[1]
    }

    // 置くことができるかをboolean で返す
    fun canset(Coordinates: IntArray, Range: Array<IntArray>, player: condition): Boolean{
        val candidate = this.limitation(player)
        val rangecoordinate = this.rangetocoordinate(Range)

//        for ((x, y) in rangecoordinate){
//            println(x.toString() + y.toString())
//        }
//
//        println("candidate:")
//        for (value in candidate){
//            println(value)
//        }
//        println("-------")

        // candidate を少なくとも含んでいるか
        var f1 = false

        for ((x, y) in rangecoordinate){
            //
            val vx = x + Coordinates[0]
            val vy = y + Coordinates[1]

            if ((vx < 0) || (vx >= this.gridmap.size)){
                return false
            }
            if ((vy < 0) || (vy >= this.gridmap[0].size)){
                return false
            }

            val coordid = this.id(intArrayOf(vx, vy))
//
//            println(coordid)
            // candidate を含んでいるか
            if (coordid in candidate){
                f1 = true
            }

            // 該当マスがすでに埋まっていたら置くことができない
            if (this.gridmap[vx][vy] !== condition.Empty){
                return false
            }
        }

        return f1
    }


    // 実際に置く. 置けない場合でも実行されるので注意
    fun setcolor(Coordinates: IntArray, Range: Array<IntArray>, player: condition){
        val rangeCoordinate = this.rangetocoordinate(Range)
        for ((x, y) in rangeCoordinate){
            this.gridmap[x+Coordinates[0]][y+Coordinates[1]] = player
        }
    }
}