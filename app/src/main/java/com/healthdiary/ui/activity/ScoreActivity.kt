package com.healthdiary.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityScoreBinding
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.repository.UserRepository
import com.healthdiary.utils.SharedPreferencesUtils
import com.healthdiary.viewmodel.CheckInRecordViewModel
import com.healthdiary.viewmodel.UserViewModel

class ScoreActivity : BaseActivity() {
    private lateinit var binding: ActivityScoreBinding

    private val checkInRecordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Provider(UserRepository.repository)
    }

    //channel
    private val channelId = "test"
    private val channelName = "测试通知"
    private val importance = NotificationManagerCompat.IMPORTANCE_HIGH
    //notification
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification_low: Notification
    private lateinit var notification_high: Notification
    private val notificationId = 1
    var height : Number = 0
    var weight : Number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()



        ///Get system notification service
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotification()




        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        var healthScore = 100
        if (isLogin()){
            userViewModel.getUser(getUserEmail()!!).observe(this){
                if (it != null){
                    if (it.height != null && it.weight != null){
                        height = it.height!!
                        weight = it.weight!!
                        val bmi = weight.toDouble() / (height.toDouble() / 100 * height.toDouble() / 100)
                        if (bmi < 18.5) {
                            healthScore -= 20
                            binding.cvBmiScore.visibility = View.VISIBLE
                            binding.tvBmiScore.text = getString(R.string.thin)
                            binding.tvBmiScoreValue.text = "-20"
                        } else if (bmi >= 25 && bmi < 30) {
                            healthScore -= 20
                            binding.cvBmiScore.visibility = View.VISIBLE
                            binding.tvBmiScore.text = getString(R.string.obese)
                            binding.tvBmiScoreValue.text = "-20"
                        } else if (bmi >= 30 && bmi < 35) {
                            healthScore -= 30
                            binding.cvBmiScore.visibility = View.VISIBLE
                            binding.tvBmiScore.text = getString(R.string.obese)
                            binding.tvBmiScoreValue.text = "-30"
                        } else if (bmi >= 35 && bmi < 40) {
                            healthScore -= 50
                            binding.cvBmiScore.visibility = View.VISIBLE
                            binding.tvBmiScore.text = getString(R.string.obese)
                            binding.tvBmiScoreValue.text = "-50"
                        } else if (bmi >= 40) {
                            healthScore -= 70
                            binding.cvBmiScore.visibility = View.VISIBLE
                            binding.tvBmiScore.text = getString(R.string.obese)
                            binding.tvBmiScoreValue.text = "-70"
                        } else {
                            binding.cvBmiScore.visibility = View.GONE
                        }
                    }
                    else{
                        ToastUtils.showShort(getString(R.string.please_fill_in_height_and_weight))
                        startActivity(Intent(this, ProfileActivity::class.java))
                    }
                }
                LogUtils.e(healthScore)
                checkInRecordViewModel.checkRecord(getUserEmail().toString(),getToday()).observe(this){ it1 ->
                    if (it1 != "NotCheckInYet"){
                        healthScore -= 20
                        binding.cvCheckInScore.visibility = View.VISIBLE
                    }
                    else{
                        binding.cvCheckInScore.visibility = View.GONE
                    }
                    binding.healthScore.text = healthScore.toString()
                    SharedPreferencesUtils.setParam("healthScoreEmail",getUserEmail().toString())
                    SharedPreferencesUtils.setNumberParam("healthScore",healthScore)

                    if(healthScore<=60 && canNotify()){
                        notificationManager.notify(notificationId, notification_low)
                    }else if(healthScore>60 && canNotify()){
                        notificationManager.notify(notificationId, notification_high)
                    }
                }
            }
        }
        else{
            ToastUtils.showShort(getString(R.string.please_login))
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    /**
     * Create notification channel
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, importance: Int) =
        notificationManager.createNotificationChannel(NotificationChannel(channelId, channelName, importance))

    /**
     * Initialize notification
     */
    private fun initNotification() {
        notification_low = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create notification channel
            createNotificationChannel(channelId,channelName,importance)
            NotificationCompat.Builder(this, channelId)
        } else {
            NotificationCompat.Builder(this)
        }.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            setContentTitle("Health Status Reminder")
            setContentText("Kindly care more about you health...")
        }.build()

        notification_high = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create notification channel
            createNotificationChannel(channelId,channelName,importance)
            NotificationCompat.Builder(this, channelId)
        } else {
            NotificationCompat.Builder(this)
        }.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            setContentTitle("Health Status Reminder")
            setContentText("Good job. Keep it up.")
        }.build()
    }
}
