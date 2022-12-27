package com.healthdiary.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityScoreBinding

class ScoreActivity : BaseActivity() {
    private lateinit var binding: ActivityScoreBinding

    //渠道Id
    private val channelId = "test"
    //渠道名
    private val channelName = "测试通知"
    //渠道重要级
    private val importance = NotificationManagerCompat.IMPORTANCE_HIGH
    //通知管理者
    private lateinit var notificationManager: NotificationManager
    //通知
    private lateinit var notification: Notification
    //通知Id
    private val notificationId = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        val textView: TextView = findViewById(R.id.health_score) as TextView
        val str: String = textView.text.toString()
        var healthScore:Int = str.toInt()

        ///获取系统通知服务
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //初始化通知
        initNotification()


        if(healthScore<=200 && canNotify()){
            notificationManager.notify(notificationId, notification)
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    /**
     * 创建通知渠道
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, importance: Int) =
        notificationManager.createNotificationChannel(NotificationChannel(channelId, channelName, importance))

    /**
     * 初始化通知
     */
    private fun initNotification() {
        notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通知渠道
            createNotificationChannel(channelId,channelName,importance)
            NotificationCompat.Builder(this, channelId)
        } else {
            NotificationCompat.Builder(this)
        }.apply {
            setSmallIcon(R.mipmap.ic_launcher)//小图标（显示在状态栏）
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))//大图标（显示在通知上）
            setContentTitle("Health Status Reminder")//标题
            setContentText("Care more about you health...")//内容
        }.build()
    }
}
