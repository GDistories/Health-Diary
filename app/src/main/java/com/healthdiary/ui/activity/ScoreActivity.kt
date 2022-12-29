package com.healthdiary.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityScoreBinding
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.viewmodel.CheckInRecordViewModel

class ScoreActivity : BaseActivity() {
    private lateinit var binding: ActivityScoreBinding

    private val checkInRecordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        val healthScore:Int = binding.healthScore.text.toString().toInt()

        ///Get system notification service
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotification()

        if (isLogin()){

            checkInRecordViewModel.checkRecord(getUserEmail().toString(),getToday()).observe(this){
                if (it != "NotCheckInYet"){
                    //TODO: 今天已经打卡
                }
                else {
                    //TODO: 今天还未打卡
                }
            }
        }

        if(healthScore<=140 && canNotify()){
            notificationManager.notify(notificationId, notification_low)
        }else if(healthScore>140 && canNotify()){
            notificationManager.notify(notificationId, notification_high)
        }
        binding.ivBack.setOnClickListener {
            finish()
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
