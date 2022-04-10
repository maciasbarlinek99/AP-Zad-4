package com.example.zadanie_4

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var status = 0
    private var wynik_gracza1 = 0
    private var wynik_gracza2 = 0
    private var resultTime : Long = 0
    private var startTime : Long = 0

    lateinit var startButton : Button
    lateinit var pushMeButton_player1 : Button
    lateinit var pushMeButton_player2 : Button
    lateinit var nickNameEditButton_player1 : Button
    lateinit var nickNameEditButton_player2 : Button

    lateinit var timeResult : TextView
    lateinit var nickNameText_player1 : TextView
    lateinit var nickNameText_player2 : TextView
    lateinit var nickNameEdit_player1 : TextView
    lateinit var nickNameEdit_player2 : TextView
    lateinit var wynik_player1 : TextView
    lateinit var wynik_player2 : TextView

    lateinit var circleWait : ImageView
    lateinit var circleReady : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)
        pushMeButton_player1 = findViewById(R.id.player1_button)
        pushMeButton_player2 = findViewById(R.id.player2_button)
        nickNameEditButton_player1 = findViewById(R.id.player1_nickname_edit_button)
        nickNameEditButton_player2 = findViewById(R.id.player2_nickname_edit_button)

        timeResult = findViewById(R.id.time_result)
        nickNameText_player1 = findViewById(R.id.player1_nickname)
        nickNameText_player2 = findViewById(R.id.player2_nickname)
        nickNameEdit_player1 = findViewById(R.id.player1_nickname_edit)
        nickNameEdit_player2 = findViewById(R.id.player2_nickname_edit)

        wynik_player1 = findViewById(R.id.score_player1)
        wynik_player2 = findViewById(R.id.score_player2)

        circleWait = findViewById(R.id.start_circle_wait)
        circleReady = findViewById(R.id.start_circle_ready)

        findViewById<TextView>(R.id.player1_nickname_edit_button).setOnClickListener{
            addNickNamePlayer(1, it)
        }

        findViewById<TextView>(R.id.player1_nickname).setOnClickListener{
            updateNickNamePlayer(1)
        }

        findViewById<TextView>(R.id.player2_nickname_edit_button).setOnClickListener{
            addNickNamePlayer(2, it)
        }

        findViewById<TextView>(R.id.player2_nickname).setOnClickListener{
            updateNickNamePlayer(2)
        }

        findViewById<Button>(R.id.start_button).setOnClickListener{
            startGame(it)
        }

        findViewById<Button>(R.id.player1_button).setOnClickListener{
            pushButton(1)
        }

        findViewById<Button>(R.id.player2_button).setOnClickListener{
            pushButton(2)
        }
    }

    private fun addNickNamePlayer(player : Int, view: View)
    {
        if(status == 0)
        {
            if(player == 1)
            {
                pushMeButton_player1.visibility = View.VISIBLE
                nickNameEditButton_player1.visibility = View.GONE
                nickNameText_player1.text = nickNameEdit_player1.text
                nickNameText_player1.visibility = View.VISIBLE
                nickNameEdit_player1.visibility = View.GONE
            }
            else
            {
                pushMeButton_player2.visibility = View.VISIBLE
                nickNameEditButton_player2.visibility = View.GONE
                nickNameText_player2.text = nickNameEdit_player2.text
                nickNameText_player2.visibility = View.VISIBLE
                nickNameEdit_player2.visibility = View.GONE
            }
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun updateNickNamePlayer(player : Int)
    {
        if(status == 0)
        {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if(player == 1)
            {
                pushMeButton_player1.visibility = View.GONE
                nickNameEditButton_player1.visibility = View.VISIBLE
                nickNameText_player1.visibility = View.GONE
                nickNameEdit_player1.visibility = View.VISIBLE
                nickNameEdit_player1.requestFocus()
                imm.showSoftInput(nickNameEdit_player1, 0)
            }
            else
            {
                pushMeButton_player2.visibility = View.GONE
                nickNameEditButton_player2.visibility = View.VISIBLE
                nickNameText_player2.visibility = View.GONE
                nickNameEdit_player2.visibility = View.VISIBLE
                nickNameEdit_player2.requestFocus()
                imm.showSoftInput(nickNameEdit_player2, 0)
            }
        }
    }

    private fun showCircle()
    {
        if(status == 0) // Nie zaczęto gry
        {
            startButton.visibility = View.VISIBLE
            circleWait.visibility = View.GONE
            circleReady.visibility = View.GONE
        }
        else if(status == 1) // Oczekiwanie na rozpoczęcie rundy
        {
            startButton.visibility = View.GONE
            circleWait.visibility = View.VISIBLE
            circleReady.visibility = View.GONE
        }
        else if(status == 2) // Runda została rozpoczęta
        {
            startButton.visibility = View.GONE
            circleWait.visibility = View.GONE
            circleReady.visibility = View.VISIBLE
        }
    }

    private fun startGame(view: View)
    {
        if(status == 0)
        {
            status = 1
            showCircle()
            Toast.makeText(this, "Gra została rozpoczęta", Toast.LENGTH_SHORT).show()

            wynik_gracza1 = 0
            wynik_gracza2 = 0
            wynik_player1.text = wynik_gracza1.toString()
            wynik_player2.text = wynik_gracza2.toString()
            timeResult.text = "0 ms"

            pushMeButton_player1.visibility = View.VISIBLE
            pushMeButton_player2.visibility = View.VISIBLE
            nickNameEditButton_player1.visibility = View.GONE
            nickNameEditButton_player2.visibility = View.GONE
            nickNameText_player1.text = nickNameEdit_player1.text
            nickNameText_player1.visibility = View.VISIBLE
            nickNameEdit_player1.visibility = View.GONE
            nickNameText_player2.text = nickNameEdit_player2.text
            nickNameText_player2.visibility = View.VISIBLE
            nickNameEdit_player2.visibility = View.GONE

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            object: CountDownTimer((1500..4500).random().toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    if(this@MainActivity.status == 1)
                    {
                        this@MainActivity.status = 2
                        this@MainActivity.showCircle()
                        startTime = System.currentTimeMillis()
                    }
                }
            }.start()
        }
    }

    private fun pushButton(player : Int)
    {
        if(status == 1 || status == 2)
        {
            if(status == 1)
            {
                if(player == 1)
                {
                    wynik_gracza2++
                    wynik_player2.text = wynik_gracza2.toString()
                }
                else
                {
                    wynik_gracza1++
                    wynik_player1.text = wynik_gracza1.toString()
                }
                timeResult.text = "Za wcześnie!"
            }
            else if(status == 2)
            {
                if(player == 1)
                {
                    wynik_gracza1++
                    wynik_player1.text = wynik_gracza1.toString()
                }
                else if(player == 2)
                {
                    wynik_gracza2++
                    wynik_player2.text = wynik_gracza2.toString()
                }
                resultTime = System.currentTimeMillis() - startTime
                timeResult.text = resultTime.toString() + " ms"
            }
            if(wynik_gracza1 == 3 || wynik_gracza2 == 3)
            {
                status = 0
                showCircle()
                if(wynik_gracza1 == 3)
                {
                    startButton.text = "Wygrał ${nickNameText_player1.text}\nKliknij aby rozpocząć grę"
                }
                else
                {
                    startButton.text = "Wygrał ${nickNameText_player2.text}\nKliknij aby rozpocząć grę"
                }
                Toast.makeText(this, "Gra została zakończona", Toast.LENGTH_SHORT).show()
            }
            else
            {
                status = 1
                showCircle()
                object: CountDownTimer((1500..4500).random().toLong(), 1) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        if(this@MainActivity.status == 1)
                        {
                            this@MainActivity.status = 2
                            this@MainActivity.showCircle()
                            startTime = System.currentTimeMillis()
                        }
                    }
                }.start()
            }
        }
    }
}