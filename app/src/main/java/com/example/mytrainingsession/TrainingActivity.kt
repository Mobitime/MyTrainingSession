package com.example.mytrainingsession



import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pl.droidsonroids.gif.GifImageView
import java.time.Duration


class TrainingActivity : AppCompatActivity() {

    private lateinit var tvExerciseName: TextView
    private lateinit var tvExerciseInstruction: TextView
    private lateinit var tvTimer: TextView
    private lateinit var gifImageView: GifImageView
    private lateinit var btnStartExercise: Button
    private lateinit var btnNextExercise: Button

    private val exercise = listOf(Exercise(
        "Приседания",
        "Выполните 10 приседаний. Спина прямая, колени не выходят за носки.",
        R.drawable.st
    ), Exercise(
        "Отжимания",
        "Сделайте 10 отжиманий. Тело ровное, локти прижаты к корпусу.",
        R.drawable.pl
    ), Exercise(
        "Прыжки",
        "Прыгайте на месте 30 секунд. Приземляйтесь мягко.",
        R.drawable.ju
    ), Exercise(
        "Бег",
        "Бег на месте 30 секунд. Поднимайте колени высоко.",
        R.drawable.rn
    ), Exercise(
        "Планка",
        "Держите планку 30 секунд. Следите за прямой линией тела.",
        R.drawable.planka
    ), Exercise(
        "Тяга",
        "Выполните 10 повторений тяги. Сохраняйте ровную спину.",
        R.drawable.mp
    ), Exercise(
        "Становая тяга", "Сделайте 10 повторений становой тяги. Работайте ногами и корпусом.",
        R.drawable.gp
    ), Exercise(
        "Жим",
        "Выполните 10 повторений жима лежа. Контролируйте движение.",
        R.drawable.gerl
    ), Exercise(
        "Подтягивания",
        "Сделайте 5 подтягиваний. Локти направлены вниз.",
        R.drawable.pul
    ),
        Exercise(
            "Пресс",
            "Сделайте скручивания 30 секунд. Работайте только корпусом.", R.drawable.pr)
    )

    private var currentExerciseIndex = 0
    private var timeDurationInMillis = 30_000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_training)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        tvExerciseName = findViewById(R.id.tvExerciseName)
        tvExerciseInstruction = findViewById(R.id.tvExerciseInstruction)
        tvTimer = findViewById(R.id.tvTimer)
        gifImageView = findViewById(R.id.gifExerciseImage)
        btnNextExercise = findViewById(R.id.btnNextExercise)
        btnStartExercise = findViewById(R.id.btnStartExercise)

        val exerciseIndex = intent.getIntExtra("EXERCISE_INDEX", 0)
        currentExerciseIndex = exerciseIndex

        btnNextExercise.isEnabled = false

        startExercise(currentExerciseIndex)

        btnStartExercise.setOnClickListener{
            btnStartExercise.isEnabled =false
            startTimer()
        }

        btnNextExercise.setOnClickListener{
            currentExerciseIndex++
            if (currentExerciseIndex < exercise.size){
                btnStartExercise.isEnabled = true
                startExercise(currentExerciseIndex)
            }else{
                Toast.makeText(this, "Тренировка завершена", Toast.LENGTH_LONG).show()
                btnNextExercise.isEnabled = false
            }
        }
    }

    private fun startExercise(index: Int) {
        val exercise = exercise[index]
        tvExerciseName.text = exercise.name
        tvExerciseInstruction.text = exercise.instruction
        gifImageView.setImageResource(exercise.gifResource)
        tvTimer.text = "30 секунд"
        btnNextExercise.isEnabled = false

    }

    private fun startTimer() {
        object : CountDownTimer(timeDurationInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Осталось: ${millisUntilFinished / 1000} сек."
            }

            override fun onFinish() {
                tvTimer.text = "Время вышло"
                btnNextExercise.isEnabled = true
            }
        }.start()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_training, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_exit -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



