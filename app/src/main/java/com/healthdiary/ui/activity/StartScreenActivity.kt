package com.healthdiary.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.healthdiary.R
import com.healthdiary.anim.SpringScaleInterpolator
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityStartScreenBinding


class StartScreenActivity : BaseActivity() {

    private val SPLASH_TIME_OUT = 5000
    var startScreenHandler = Handler(Looper.getMainLooper())
    var skipHandler = Handler(Looper.getMainLooper())
    var animHandler = Handler(Looper.getMainLooper())
    var startScreenRunnable: Runnable? = null
    var skipRunnable: Runnable? = null
    var animRunnable1: Runnable? = null
    var animRunnable2: Runnable? = null
    var animRunnable3: Runnable? = null
    var animRunnable4: Runnable? = null
    var logoAnimation3: Animation? = null
    var logoAnimation4: Animation? = null

    private val TAG = "StartScreenActivity"
    var times: Int = SPLASH_TIME_OUT / 1000

    private lateinit var binding: ActivityStartScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartScreenBinding.inflate(layoutInflater)
        logoAnimation3 = AnimationUtils.loadAnimation(this, R.anim.logo_animation_disappear)
        logoAnimation4 = AnimationUtils.loadAnimation(this, R.anim.logo_animation_appear)

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


        animRunnable1 = Runnable {
            logoAnimOne()
        }
        animRunnable2 = Runnable {
            logoAnimTwo()
        }
        animRunnable3 = Runnable {
            binding.logoHeartImage.startAnimation(logoAnimation3)
            binding.logoHeartImage.visibility = View.GONE
        }
        animRunnable4 = Runnable {

            binding.logoImage.startAnimation(logoAnimation4)
            binding.logoImage.visibility = View.VISIBLE
        }
        animHandler.postDelayed(animRunnable1!!, 100)
        animHandler.postDelayed(animRunnable2!!, 1300)
        animHandler.postDelayed(animRunnable3!!, 2300)
        animHandler.postDelayed(animRunnable4!!, 2800)


        val view = binding.root
        setContentView(view)
    }

    fun skipStartScreen(view: View?) {
        startActivity(Intent(this, MainActivity::class.java))
        startScreenHandler.removeCallbacks(startScreenRunnable!!)
        skipHandler.removeCallbacks(skipRunnable!!)
        finish()
    }

    private fun logoAnimOne(): Unit{
        val animatorX: ObjectAnimator = ObjectAnimator.ofFloat(binding.logoHeartImage, "translationX", 0f, 0f)
        val animatorY: ObjectAnimator = ObjectAnimator.ofFloat(binding.logoHeartImage, "translationY", -1000f, 0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = SpringScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }

    private fun logoAnimTwo(): Unit{
        val animatorX: ObjectAnimator = ObjectAnimator.ofFloat(binding.logoHeartImage, "scaleX", 0.2f, 0.8f)
        val animatorY: ObjectAnimator = ObjectAnimator.ofFloat(binding.logoHeartImage, "scaleY", 0.2f, 0.8f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = SpringScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }

}