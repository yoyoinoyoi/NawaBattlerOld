package com.example.kotori

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "ホーム"

    }

    // 実行ボタンタップ時
    fun onButtonBattle(view: View?){
        val intent = Intent(this, LobbyActivity::class.java)
        startActivity(intent)
    }

    // 実行ボタンタップ時
    fun onButtonSub(view: View?){
        val intent = Intent(this, DeckActivity::class.java)
        startActivity(intent)
    }

}
