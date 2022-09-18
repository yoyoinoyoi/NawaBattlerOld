package com.example.kotori

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }
    }

    private fun rollDice() {
        val resultText: TextView = findViewById(R.id.result_text)
        val randomInt = (1..6).random()
        resultText.text = randomInt.toString()
    }

    // 実行ボタンタップ時
    fun onButtonBattle(view: View?){
        val intent = Intent(this, BattleActivity::class.java)
        startActivity(intent)
    }

    // 実行ボタンタップ時
    fun onButtonSub(view: View?){
        val intent = Intent(this, SubActivity::class.java)
        startActivity(intent)
    }

}
