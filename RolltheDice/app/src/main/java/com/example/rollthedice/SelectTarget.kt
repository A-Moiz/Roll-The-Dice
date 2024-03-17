package com.example.rollthedice

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SelectTarget : AppCompatActivity() {
    // Different target numbers
    lateinit var option1: Button
    lateinit var option2: Button
    lateinit var option3: Button
    lateinit var option4: Button

    // Back and Next button
    lateinit var backBtn: Button
    lateinit var nextBtn: Button

    // Setting the target number
    var targetNum: Int = 0

    // Avatar selected from previous screen
    var avatar: Int = 0

    // Player/Computer wins
    var playerWins: Int = 0
    var computerWins: Int = 0

    // Checking if user has selected a target number
    var targetSelected: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_target)

        playerWins = intent.getIntExtra("PLAYER_WINS", 0)
        computerWins = intent.getIntExtra("COMPUTER_WINS", 0)

        option1 = findViewById<Button>(R.id.option1)
        option2 = findViewById<Button>(R.id.option2)
        option3 = findViewById<Button>(R.id.option3)
        option4 = findViewById<Button>(R.id.option4)
        backBtn = findViewById<Button>(R.id.backBtn)
        nextBtn = findViewById<Button>(R.id.nextBtn)

        // Getting avatar
        avatar = intent.getIntExtra("SELECTED_ICON", 0)

        option1.setOnClickListener() {
            targetNum = 100
            targetSelected = true
            option1.setBackgroundColor(Color.MAGENTA)
            option2.setBackgroundColor(0)
            option3.setBackgroundColor(0)
            option4.setBackgroundColor(0)
        }

        option2.setOnClickListener() {
            targetNum = 150
            targetSelected = true
            option2.setBackgroundColor(Color.MAGENTA)
            option1.setBackgroundColor(0)
            option3.setBackgroundColor(0)
            option4.setBackgroundColor(0)
        }

        option3.setOnClickListener() {
            targetNum = 200
            targetSelected = true
            option3.setBackgroundColor(Color.MAGENTA)
            option2.setBackgroundColor(0)
            option1.setBackgroundColor(0)
            option4.setBackgroundColor(0)
        }

        option4.setOnClickListener() {
            targetNum = 250
            targetSelected = true
            option4.setBackgroundColor(Color.MAGENTA)
            option2.setBackgroundColor(0)
            option3.setBackgroundColor(0)
            option1.setBackgroundColor(0)
        }

        backBtn.setOnClickListener() {
            val intent = Intent(this, SelectAvatar::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener() {
            if (targetSelected) {
                Intent(this, GameView::class.java).also {
                    it.putExtra("SELECTED_TARGET", targetNum)
                    it.putExtra("AVATAR", avatar)
                    it.putExtra("PLAYER_WINS", playerWins)
                    it.putExtra("COMPUTER_WINS", computerWins)
                    startActivity(it)
                }
            } else {
                targetMissing()
            }
        }
    }

    fun targetMissing() {
        Toast.makeText(this, "Please select a target", Toast.LENGTH_SHORT).show()
    }
}