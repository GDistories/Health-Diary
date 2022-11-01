package com.healthdiary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.healthdiary.databinding.ActivityStartScreenBinding


class StartScreenActivity : BaseActivity() {

    private val SPLASH_TIME_OUT = 5000
    var startScreenHandler = Handler(Looper.getMainLooper())
    var skipHandler = Handler(Looper.getMainLooper())
    var startScreenRunnable: Runnable? = null
    var skipRunnable: Runnable? = null
    private val TAG = "StartScreenActivity"
    var times: Int = SPLASH_TIME_OUT / 1000

    private lateinit var binding: ActivityStartScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)

        hideStatusAndActionBar()
        setContentView(R.layout.activity_start_screen)

        startScreenRunnable = Runnable {
            val intent = Intent(this@StartScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        skipRunnable = Runnable {
            binding.btnSkip.text = this@StartScreenActivity.getString(R.string.skip) + "(" +times + ")"
            times -= 1
            if (times != 0) {
                skipHandler.postDelayed(skipRunnable!!, 1000)
            }
        }

        skipHandler.postDelayed(
            skipRunnable!!,
            0)
        startScreenHandler.postDelayed(
            startScreenRunnable!!,
            SPLASH_TIME_OUT.toLong()
        )


        val view = binding.root
        setContentView(view)
    }

    fun skipStartScreen(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
        startScreenHandler.removeCallbacks(startScreenRunnable!!)
        skipHandler.removeCallbacks(skipRunnable!!)
        finish()
    }

}