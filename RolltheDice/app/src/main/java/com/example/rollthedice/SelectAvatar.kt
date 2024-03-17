package com.example.rollthedice

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class SelectAvatar : AppCompatActivity() {
    // Avatars available
    lateinit var icon1: ImageView
    lateinit var icon2: ImageView
    lateinit var icon3: ImageView
    lateinit var icon4: ImageView

    // Back and Next button
    lateinit var backBtn: Button
    lateinit var nextBtn: Button

    // Setting the avatar to a variable
    var selectedIcon: Int = 0

    // Checking if user has picked an avatar
    var iconPicked: Boolean = false

    // Player/Computer wins
    var playerWins: Int = 0
    var computerWins: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_avatar)

        playerWins = intent.getIntExtra("PLAYER_WINS", 0)
        computerWins = intent.getIntExtra("COMPUTER_WINS", 0)

        icon1 = findViewById<ImageView>(R.id.iconOne)
        icon2 = findViewById<ImageView>(R.id.iconTwo)
        icon3 = findViewById<ImageView>(R.id.iconThree)
        icon4 = findViewById<ImageView>(R.id.iconFour)

        backBtn = findViewById<Button>(R.id.backBtn)
        nextBtn = findViewById<Button>(R.id.nextBtn)

        icon1.setImageResource(R.drawable.luffy_icon)
        icon2.setImageResource(R.drawable.zoro_icon)
        icon3.setImageResource(R.drawable.nami_icon)
        icon4.setImageResource(R.drawable.robin_icon)

        icon1.setOnClickListener() {
            selectedIcon = R.drawable.luffy_icon
            iconPicked = true
            icon1.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        icon2.setOnClickListener() {
            selectedIcon = R.drawable.zoro_icon
            iconPicked = true
            icon2.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        icon3.setOnClickListener() {
            selectedIcon = R.drawable.nami_icon
            iconPicked = true
            icon3.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        icon4.setOnClickListener() {
            selectedIcon = R.drawable.robin_icon
            iconPicked = true
            icon4.animate().apply {
                duration = 1000
                rotationYBy(360f)
            }.start()
        }

        backBtn.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        nextBtn.setOnClickListener() {
            if (iconPicked) {
                Intent(this, SelectTarget::class.java).also {
                    it.putExtra("SELECTED_ICON", selectedIcon)
                    it.putExtra("PLAYER_WINS", playerWins)
                    it.putExtra("COMPUTER_WINS", computerWins)
                    startActivity(it)
                }
            } else {
                avatarMissing()
            }
        }
    }

    fun avatarMissing() {
        Toast.makeText(this, "Please select an avatar", Toast.LENGTH_SHORT).show()
    }
}