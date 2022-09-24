package com.example.kotori

class DeckField() {
    var deck: MutableList<Card>
    var handCard: MutableList<Int>
    var stack: MutableList<Int>

    init {
        this.deck = mutableListOf<Card>()
        this.handCard = mutableListOf<Int>()
        this.stack = mutableListOf<Int>()
    }

    // index は0-indexed
    // 手札の index 番目のカードを選択したら、そのカードの効果を返す
    fun playCard(index: Int): Array<IntArray>{
        val choiceCard = this.handCard[index]
        val selectedCard = this.stack[(0 until this.stack.size).random()]
        this.stack.remove(selectedCard)
        this.handCard[index] = selectedCard
        return this.deck[choiceCard].Range
    }

    // フロントへ表示するときに使うメソッド
    // 手札のカードのImage を返す
    fun imageArray(): MutableList<Int>{
        val ret = mutableListOf<Int>()
        for (imageIndex in this.handCard){

            // -1(何もない) 場合には白の画像を挿入
            if (imageIndex == -1){
                ret.add(R.drawable.white)
            }
            else {
                ret.add(this.deck[imageIndex].Image)
            }
        }
        return ret
    }

    // デッキが完成したら、手札を充てんする
    fun reload(){
        this.stack = mutableListOf<Int>()
        for (i in 0 until this.deck.size){
            this.stack.add(i)
        }

        this.handCard = mutableListOf(-1, -1, -1)
        // 一枚カードを引く
        // 同じことを3回繰り返す
        // デッキ内のカードは必ず3枚以上にすること
        for (i in 0 until 3) {
            this.choice(i)
        }
    }

    //
    fun choice(index: Int){
        val length = this.stack.size
        // 山札が尽きたら 何もない(-1) を手札に加える
        if (length == 0){
            this.handCard[index] = -1
        } else {
            val selectedCard = this.stack[(0 until length).random()]
            this.stack.remove(selectedCard)
            this.handCard[index] = selectedCard
        }
    }

}