package com.example.rollthedice

// Link to video: https://drive.google.com/drive/folders/1KBCdIFT2QYuvYfqxTdzlqHxMzUBP8wxA?usp=share_link

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // Dialogs
    lateinit var aboutDialog: Dialog
    lateinit var rulesDialog: Dialog

    // About, New Game, Rules, Game Data, Reset Buttons
    lateinit var aboutBtn: Button
    lateinit var newGameBtn: Button
    lateinit var rulesBtn: Button
    lateinit var resetBtn: Button

    // Player/Computer wins
    var playerWins: Int = 0
    var computerWins: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerWins = intent.getIntExtra("PLAYER_WINS", 0)
        computerWins = intent.getIntExtra("COMPUTER_WINS", 0)

        // Setting up all the buttons
        aboutBtn = findViewById<Button>(R.id.aboutBtn)
        rulesBtn = findViewById<Button>(R.id.rulesBtn)
        newGameBtn = findViewById<Button>(R.id.newGameBtn)
        resetBtn = findViewById<Button>(R.id.resetBtn)

        // Setting up the Dialogs
        aboutDialog = Dialog(this)
        rulesDialog = Dialog(this)

        // Clear Scores button
        resetBtn.setOnClickListener() {
            playerWins = 0
            computerWins = 0
            Toast.makeText(this, "Scores have been reset for player and computer", Toast.LENGTH_SHORT).show()
        }
        // About button
        aboutBtn.setOnClickListener() {
            aboutDialog.setContentView(R.layout.about_popup)
            aboutDialog.setCancelable(true)
            aboutDialog.setContentView(R.layout.about_popup)
            aboutDialog.show()
        }

        // Rules button
        rulesBtn.setOnClickListener() {
            rulesDialog.setContentView(R.layout.rules_popup)
            rulesDialog.setCancelable(true)
            rulesDialog.setContentView(R.layout.rules_popup)
            rulesDialog.show()
        }

        // New Game button
        newGameBtn.setOnClickListener() {
            val intent = Intent(this, SelectAvatar::class.java).also {
                it.putExtra("PLAYER_WINS", playerWins)
                it.putExtra("COMPUTER_WINS", computerWins)
            }
            startActivity(intent)
        }
    }
}