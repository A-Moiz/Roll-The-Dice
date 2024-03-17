package com.example.rollthedice

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ResultScreen : AppCompatActivity() {
    // Gif image
    lateinit var img: ImageView

    // Using number to check who won
    var resultNum: Int = 0

    // Texts in result screen
    lateinit var text: TextView
    lateinit var totalWins: TextView

    // Number of wins for player/computer
    var playerWins: Int = 0
    var computerWins: Int = 0

    // Exit button to go back to the main menu
    lateinit var exitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_screen)

        playerWins = intent.getIntExtra("PLAYER_WINS", 0)
        computerWins = intent.getIntExtra("COMPUTER_WINS", 0)

        // Getting IDs
        img = findViewById<ImageView>(R.id.gifImg)
        text = findViewById<TextView>(R.id.displayResult)
        totalWins = findViewById<TextView>(R.id.totalWins)
        exitBtn = findViewById<Button>(R.id.exitBtn)

        // Getting result from game screen
        resultNum = intent.getIntExtra("RESULT", 0)

        if (resultNum == 1) {
            img.setImageResource(R.drawable.win_screen)
            text.setText("You Win!")
            text.setTextColor(Color.GREEN)
            playerWins++
        } else {
            img.setImageResource(R.drawable.lose_screen)
            text.setText("You Lose!")
            text.setTextColor(Color.RED)
            computerWins++
        }
        totalWins.setText("H:" + playerWins + "/C:" + computerWins)

        exitBtn.setOnClickListener() {
            Intent(this, MainActivity::class.java).also {
                it.putExtra("PLAYER_WINS", playerWins)
                it.putExtra("COMPUTER_WINS", computerWins)
                startActivity(it)
            }
        }
    }
}