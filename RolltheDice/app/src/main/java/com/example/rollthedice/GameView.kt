package com.example.rollthedice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.BoringLayout
import android.text.TextPaint
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class GameView : AppCompatActivity() {
    // Target number for game
    var targetNum: Int = 0

    // Selected avatar
    var avatar: Int = 0

    // Target score text
    lateinit var targetScoreTxt: TextView

    // Player and Computer avatar
    lateinit var playerAvatar: ImageView
    lateinit var computerAvatar: ImageView

    // Player Dices
    lateinit var pD1: ImageView
    lateinit var pD2: ImageView
    lateinit var pD3: ImageView
    lateinit var pD4: ImageView
    lateinit var pD5: ImageView

    // Computer dices
    lateinit var cD1: ImageView
    lateinit var cD2: ImageView
    lateinit var cD3: ImageView
    lateinit var cD4: ImageView
    lateinit var cD5: ImageView

    // Back, Throw, Re-roll and Score button
    lateinit var backBtn: Button
    lateinit var throwBtn: Button
    lateinit var reRollBtn: Button
    lateinit var scoreBtn: Button

    // Array to store all Player and Computer Dices
    lateinit var playerDices: ArrayList<ImageView>
    lateinit var computerDices: ArrayList<ImageView>

    // Player/Computer score total
    var playerScoreTotal: Int = 0
    var computerScoreTotal: Int = 0

    // Array to store all the Dice images
    lateinit var diceImages: ArrayList<Int>

    // Random number
    lateinit var randomNum: Random

    // Player/Computer score text
    lateinit var playerScoreTxt: TextView
    lateinit var computerScoreTxt: TextView

    // Array to store Player and Computers score per round
    lateinit var playerScore: ArrayList<Int>
    lateinit var computerScore: ArrayList<Int>

    // Player/Computer Re-Rolls count
    var playerReRolls: Int = 0
    var computerReRolls: Int = 0

    // Checking if Dice has been selected to keep
    var dice1: Boolean = false
    var dice2: Boolean = false
    var dice3: Boolean = false
    var dice4: Boolean = false
    var dice5: Boolean = false

    // Old score for computer
    var comOldScore: Int = 0

    // Array to save old dice rolls for computer
    lateinit var comOldDiceRoll: ArrayList<Int>

    // New score for computer
    var comNewScore: Int = 0

    // Player/Computer wins
    var playerWins: Int = 0
    var computerWins: Int = 0

    // dice number for player and computer
    var playerDNum: Int = 0
    var comDNum: Int = 0

    // Saving array for player and computer scores
    var pScoreArrayKey: String = "P_SCORE"
    var cScoreArrayKey: String = "C_SCORE"

    // Saving player and computer score total
    var pScoreTotalKey: String = "P_SCORE_TOTAL"
    var cScoreTotalKey: String = "C_SCORE_TOTAL"

    // Saving player and computer re-roll count
    var pReRollKey: String = "P_RE_ROLL"
    var cReRollKey: String = "C_RE_ROLL"

    // Saving dice selected boolean variables
    var d1Key: String = "DICE_1"
    var d2Key: String = "DICE_2"
    var d3Key: String = "DICE_3"
    var d4Key: String = "DICE_4"
    var d5Key: String = "DICE_5"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_view)

        // Getting target from previous activity
        targetNum = intent.getIntExtra("SELECTED_TARGET", 0)
        playerWins = intent.getIntExtra("PLAYER_WINS", 0)
        computerWins = intent.getIntExtra("COMPUTER_WINS", 0)

        // Getting avatar
        avatar = intent.getIntExtra("AVATAR", 0)

        // Setting player and Computer avatar
        playerAvatar = findViewById<ImageView>(R.id.playerAvatar)
        computerAvatar = findViewById<ImageView>(R.id.computerAvatar)
        playerAvatar.setImageResource(avatar)
        computerAvatar.setImageResource(R.drawable.npc_icon)

        // Getting all the player dices
        pD1 = findViewById<ImageView>(R.id.playerDice1)
        pD2 = findViewById<ImageView>(R.id.playerDice2)
        pD3 = findViewById<ImageView>(R.id.playerDice3)
        pD4 = findViewById<ImageView>(R.id.playerDice4)
        pD5 = findViewById<ImageView>(R.id.playerDice5)

        // Getting all the computer dices
        cD1 = findViewById<ImageView>(R.id.computerDice1)
        cD2 = findViewById<ImageView>(R.id.computerDice2)
        cD3 = findViewById<ImageView>(R.id.computerDice3)
        cD4 = findViewById<ImageView>(R.id.computerDice4)
        cD5 = findViewById<ImageView>(R.id.computerDice5)

        // Displaying chosen target score
        targetScoreTxt = findViewById<TextView>(R.id.targetScore)
        targetScoreTxt.setText("Target Score: " + targetNum)

        // Getting all the Buttons
        backBtn = findViewById<Button>(R.id.backBtn)
        throwBtn = findViewById<Button>(R.id.throwBtn)
        reRollBtn = findViewById<Button>(R.id.reRollBtn)
        scoreBtn = findViewById<Button>(R.id.scoreBtn)

        // Disable buttons
        reRollBtn.isEnabled = false
        scoreBtn.isEnabled = false

        // Player and Computer Arrays
        playerDices = arrayListOf(pD1, pD2, pD3, pD4, pD5)
        computerDices = arrayListOf(cD1, cD2, cD3, cD4, cD5)

        // Array to store all the images
        diceImages = arrayListOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3,
            R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6
        )

        // Setting up Random Number
        randomNum = Random()

        // Setting up Text Views for Score
        playerScoreTxt = findViewById(R.id.playerScore)
        computerScoreTxt = findViewById(R.id.computerScore)

        // Setting up player/computer score per round
        playerScore = ArrayList<Int>()
        computerScore = ArrayList<Int>()

        // Setting up computer old dice rolls
        comOldDiceRoll = ArrayList<Int>()

        // Back button
        backBtn.setOnClickListener() {
            val intent = Intent(this, SelectAvatar::class.java)
            startActivity(intent)
        }

        // Throw button
        throwBtn.setOnClickListener() {
            playerScore.clear()
            computerScore.clear()
            resetBoolean()
            playerDice()
            computerDice()
            throwBtn.isEnabled = false
            reRollBtn.isEnabled = true
            scoreBtn.isEnabled = true
        }

        // Score button
        scoreBtn.setOnClickListener() {
            resetBoolean()
            scoreBtn.isEnabled = false
            reRollBtn.isEnabled = false
            throwBtn.isEnabled = true
            updatePlayerScores()
            updateComputerScores()
            displayScores()
        }

        // Player Dices for re-roll
        pD1.setOnClickListener() {
            dice1 = true
            Toast.makeText(this, "Dice 1 has been selected to stay", Toast.LENGTH_SHORT).show()
        }

        // Player Dices for re-roll
        pD2.setOnClickListener() {
            dice2 = true
            Toast.makeText(this, "Dice 2 has been selected to stay", Toast.LENGTH_SHORT).show()
        }

        // Player Dices for re-roll
        pD3.setOnClickListener() {
            dice3 = true
            Toast.makeText(this, "Dice 3 has been selected to stay", Toast.LENGTH_SHORT).show()
        }

        // Player Dices for re-roll
        pD4.setOnClickListener() {
            dice4 = true
            Toast.makeText(this, "Dice 4 has been selected to stay", Toast.LENGTH_SHORT).show()
        }

        // Player Dices for re-roll
        pD5.setOnClickListener() {
            dice5 = true
            Toast.makeText(this, "Dice 5 has been selected to stay", Toast.LENGTH_SHORT).show()
        }

        // Re-roll button
        reRollBtn.setOnClickListener() {
            if (playerReRolls < 2) {
                if (!dice1) {
                    playerScore.removeAt(0)
                    reRolling(pD1, 0)
                }

                if (!dice2) {
                    playerScore.removeAt(1)
                    reRolling(pD2, 1)
                }

                if (!dice3) {
                    playerScore.removeAt(2)
                    reRolling(pD3, 2)
                }

                if (!dice4) {
                    playerScore.removeAt(3)
                    reRolling(pD4, 3)
                }

                if (!dice5) {
                    playerScore.removeAt(4)
                    reRolling(pD5, 4)
                }
                playerReRolls++
            }

            if (playerReRolls == 1) {
                computersTurn()
                resetBoolean()
            }

            if (playerReRolls == 2) {
                updatePlayerScores()
                displayScores()
                scoreBtn.isEnabled = false
                reRollBtn.isEnabled = false
                throwBtn.isEnabled = true
                playerReRolls = 0
            }
        }
    }

    // Reset boolean variables
    fun resetBoolean() {
        dice1 = false
        dice2 = false
        dice3 = false
        dice4 = false
        dice5 = false
    }

    // Throwing all player Dices
    fun playerDice() {
        for (i in playerDices) {
            playerDNum = randomNum.nextInt(6)
            i.setImageResource(diceImages[playerDNum])
            playerScore.add(playerDNum + 1)
        }
    }

    // Throwing all computer Dices
    fun computerDice() {
        for (i in computerDices) {
            comDNum = randomNum.nextInt(6)
            i.setImageResource(diceImages[comDNum])
            computerScore.add(comDNum + 1)
        }
    }

    // Updating player total score
    fun updatePlayerScores() {
        playerScoreTotal += playerScore.sum()
    }

    // Updating computer total score
    fun updateComputerScores() {
        computerScoreTotal += computerScore.sum()
    }

    // Re-rolling player Dices
    fun reRolling(dice: ImageView, indexNum: Int) {
        playerDNum = randomNum.nextInt(6)
        dice.setImageResource(diceImages[playerDNum])
        playerScore.add(indexNum, playerDNum + 1)
    }

    // Displaying player/computer scores
    fun displayScores() {
        playerScoreTxt.setText("Player Score: " + playerScoreTotal)
        computerScoreTxt.setText("Computer Score: " + computerScoreTotal)
        if (playerScoreTotal >= targetNum || computerScoreTotal >= targetNum) {
            throwBtn.isEnabled = false
            reRollBtn.isEnabled = false
            scoreBtn.isEnabled = false
            backBtn.isEnabled = true
            checkWinner()
        }
        playerScore.clear()
        computerScore.clear()
    }

    // Computer has run out of re rolls message
    fun cZeroRolls() {
        Toast.makeText(this, "Computer has run out of re-rolls", Toast.LENGTH_SHORT).show()
    }

    // Computers turn

    // Computer will choose a random number between 0 and 1.
    // If it is 0 the computer will re roll otherwise it will just score its turn
    // I am also checking if the computer score is less than a set amount, if it is
    // The computer will re roll again. The set amount will be a low number to they
    // have a high chance of winning/rolling a high score
    // Furthermore, if they have scored a high roll that will stay the same and will not re roll
    // Throughout all this I will also be keeping count of the computer re rolls as they have
    // the same number of re rolls as the player
    fun computersTurn() {
        var currentTotal = computerScore.sum()
        if (computerReRolls == 2) {
            cZeroRolls()
            updateComputerScores()
            computerScore.clear()
            computerReRolls = 0
        }
//        If re-rolls < 2
//            If 0 -> re-roll
//            If 1 -> re-roll
//            If 2 -> score
        if (playerScoreTotal >= (targetNum - 70)) {
            var decisionNum = randomNum.nextInt(3)
            if (decisionNum == 0 || decisionNum == 1) {
                computerCurrentScore()
                computerScore.clear()
                computerDice()
                computerNewScore()
                compareComScore()
                computerReRolls++
                updateComputerScores()
                computerScore.clear()
            } else {
                updateComputerScores()
                computerScore.clear()
            }
        } else {
            var decisionNum = randomNum.nextInt(2)
            if (computerReRolls < 2) {
                if (comLessThan()) {
                    computerScore.clear()
                    computerDice()
                    computerReRolls++
                    updateComputerScores()
                    computerScore.clear()
                }
                if (currentTotal in 25..30) {
                    currentTotal = 0
                } else {
                    if (decisionNum == 0) {
                        computerCurrentScore()
                        computerScore.clear()
                        computerDice()
                        computerNewScore()
                        compareComScore()
                        computerReRolls++
                        updateComputerScores()
                        computerScore.clear()
                    } else {
                        updateComputerScores()
                        computerScore.clear()
                    }
                }
            }
        }
    }

    // Compare old computer score with new score
    fun compareComScore() {
        if (comOldScore > comNewScore) {
            computerScore.clear()
            computerScore.add(comOldScore)
        } else if (comOldScore == comNewScore) {
            computerScore.clear()
            computerScore.add(comOldScore)
        } else {
            computerScore.clear()
            computerScore.add(comNewScore)
        }
    }

    // Check if current computer score is <= to a set number
    fun comLessThan(): Boolean {
        var currentTotal: Int = 0
        var lessThan: Int = 17
        for (i in computerScore) {
            currentTotal += i
        }
        return currentTotal < lessThan
    }

    // Save current computer score
    fun computerCurrentScore() {
        for (i in computerScore) {
            comOldScore += i
            comOldDiceRoll += i
        }
    }

    // Save new computer score
    fun computerNewScore() {
        for (i in computerScore) {
            comNewScore += i
        }
    }

    // Check if player score equals computer score
    fun checkSameScore(): Boolean {
        return (playerScoreTotal == computerScoreTotal)
    }

    // Final throw for player and computer
    fun finalThrow() {
        playerScore.clear()
        computerScore.clear()
        playerDice()
        computerDice()
        updatePlayerScores()
        updateComputerScores()
//        displayScores()
    }

    // Check if player won
    fun playerWon(): Boolean {
        return (playerScoreTotal >= computerScoreTotal)
    }

    // Check for a winner
    fun checkWinner() {
        while (checkSameScore()) {
            finalThrow()
        }
        if (playerScoreTotal >= targetNum && computerScoreTotal >= targetNum) {
            finalThrow()
            if (playerWon()) {
                Intent(this, ResultScreen::class.java).also {
                    it.putExtra("RESULT", 1)
                    it.putExtra("PLAYER_WINS", playerWins)
                    it.putExtra("COMPUTER_WINS", computerWins)
                    startActivity(it)
                }
            } else {
                Intent(this, ResultScreen::class.java).also {
                    it.putExtra("RESULT", 2)
                    it.putExtra("PLAYER_WINS", playerWins)
                    it.putExtra("COMPUTER_WINS", computerWins)
                    startActivity(it)
                }
            }
        }
        if (playerWon()) {
            Intent(this, ResultScreen::class.java).also {
                it.putExtra("RESULT", 1)
                it.putExtra("PLAYER_WINS", playerWins)
                it.putExtra("COMPUTER_WINS", computerWins)
                startActivity(it)
            }
        } else {
            Intent(this, ResultScreen::class.java).also {
                it.putExtra("RESULT", 2)
                it.putExtra("PLAYER_WINS", playerWins)
                it.putExtra("COMPUTER_WINS", computerWins)
                startActivity(it)
            }
        }
    }

//    // Check for a winner
//    fun checkWinner() {
//        while (checkSameScore()) {
//            finalThrow()
//        }
//        if (playerWon()) {
//            Intent(this, ResultScreen::class.java).also {
//                it.putExtra("RESULT", 1)
//                it.putExtra("PLAYER_WINS", playerWins)
//                it.putExtra("COMPUTER_WINS", computerWins)
//                startActivity(it)
//            }
//        } else {
//            Intent(this, ResultScreen::class.java).also {
//                it.putExtra("RESULT", 2)
//                it.putExtra("PLAYER_WINS", playerWins)
//                it.putExtra("COMPUTER_WINS", computerWins)
//                startActivity(it)
//            }
//        }
//    }

    // Saving values on screen
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(pReRollKey, playerReRolls)
        outState.putInt(cReRollKey, computerReRolls)
        outState.putIntegerArrayList(pScoreArrayKey, playerScore)
        outState.putIntegerArrayList(cScoreArrayKey, computerScore)
        outState.putInt(pScoreTotalKey, playerScoreTotal)
        outState.putInt(cScoreTotalKey, computerScoreTotal)
        outState.putBoolean(d1Key, dice1)
        outState.putBoolean(d2Key, dice2)
        outState.putBoolean(d3Key, dice3)
        outState.putBoolean(d4Key, dice4)
        outState.putBoolean(d5Key, dice5)
    }

    // Retrieving the values after screen has been created again
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        playerReRolls = savedInstanceState.getInt(pReRollKey)
        computerReRolls = savedInstanceState.getInt(cReRollKey)
        playerScore = savedInstanceState.getIntegerArrayList(pScoreArrayKey) as ArrayList<Int>
        computerScore = savedInstanceState.getIntegerArrayList(cScoreArrayKey) as ArrayList<Int>
        playerScoreTotal = savedInstanceState.getInt(pScoreTotalKey)
        computerScoreTotal = savedInstanceState.getInt(cScoreTotalKey)
        dice1 = savedInstanceState.getBoolean(d1Key)
        dice2 = savedInstanceState.getBoolean(d2Key)
        dice3 = savedInstanceState.getBoolean(d3Key)
        dice4 = savedInstanceState.getBoolean(d4Key)
        dice5 = savedInstanceState.getBoolean(d5Key)
        reloadScores()
        reloadPlayerDices()
        reloadComputerDices()
    }


    // Reloading player Dices
    fun reloadPlayerDices() {
        if (playerScore.isEmpty()) {
            scoreBtn.isEnabled = false
            reRollBtn.isEnabled = false
            Toast.makeText(this, "Please click on the Throw button", Toast.LENGTH_SHORT).show()
        } else {
            scoreBtn.isEnabled = true
            reRollBtn.isEnabled = true
            pD1.setImageResource(diceImages[playerScore[0]-1])
            pD2.setImageResource(diceImages[playerScore[1]-1])
            pD3.setImageResource(diceImages[playerScore[2]-1])
            pD4.setImageResource(diceImages[playerScore[3]-1])
            pD5.setImageResource(diceImages[playerScore[4]-1])
        }
    }

    // Reloading computer dices
    fun reloadComputerDices() {
        if (!computerScore.isEmpty()) {
            cD1.setImageResource(diceImages[computerScore[0]-1])
            cD2.setImageResource(diceImages[computerScore[1]-1])
            cD3.setImageResource(diceImages[computerScore[2]-1])
            cD4.setImageResource(diceImages[computerScore[3]-1])
            cD5.setImageResource(diceImages[computerScore[4]-1])
        } else {
            Toast.makeText(this, "Please click on the Throw button", Toast.LENGTH_SHORT).show()
        }
    }

    // Reloading player score
    fun reloadScores() {
        playerScoreTxt.setText("Player Score: " + playerScoreTotal)
        computerScoreTxt.setText("Computer Score: " + computerScoreTotal)
    }
}