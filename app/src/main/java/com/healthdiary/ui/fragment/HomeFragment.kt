package com.healthdiary.ui.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentHomeBinding
import com.healthdiary.ui.activity.*
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.viewmodel.CheckInRecordViewModel


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val checkInRecordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }

    //notification channel
    private val channelId = "test"
    private val channelName = "测试通知"
    private val importance = NotificationManagerCompat.IMPORTANCE_HIGH
    //notification
    private lateinit var notificationManager: NotificationManager
    private lateinit var notification_checkin: Notification
    private val notificationId = 1

    override fun onStart() {
        super.onStart()
        val status_image = view?.findViewById(R.id.home_user_statusicon) as ImageView
        val status_text = view?.findViewById(R.id.home_user_statustext) as TextView

        notificationManager = activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        initNotification()

        binding.news1.setOnClickListener {
            startActivity(Intent(activity, NewsContentActivity::class.java))
        }
        binding.checkInRecord.setOnClickListener{
            startActivity(Intent(activity, CheckInHistoryActivity::class.java))
        }
        binding.healthScore.setOnClickListener{
            startActivity(Intent(activity, ScoreActivity::class.java))
        }
        binding.healthTips.setOnClickListener{
            startActivity(Intent(activity, TipsActivity::class.java))
        }
        binding.trackData.setOnClickListener{
            startActivity(Intent(activity, TrackerActivity::class.java))
        }
        if (isLogin()){
            checkInRecordViewModel.checkRecord(getUserEmail().toString(),getToday()).observe(this){
                if (it != "NotCheckInYet"){
                    status_image.setImageResource(R.drawable.ic_checkin_success)
                    status_text.setText(R.string.home_check_status_true)
                    status_text.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                else {
                    status_image.setImageResource(R.drawable.ic_warning)
                    status_text.setText(R.string.home_check_status_false)
                    status_text.setTextColor(ContextCompat.getColor(requireContext(),R.color.warning))
                    notificationManager.notify(notificationId, notification_checkin)
                }
            }

            val healthScore = getHealthScore()
            if (healthScore == -1){
                binding.healthScore.text = "---"
            }
            else{
                binding.healthScore.text = healthScore.toString()
            }


        }
        else{
            status_image.setImageResource(R.drawable.ic_warning)
            status_text.setText(R.string.home_login_status)
            status_text.setTextColor(ContextCompat.getColor(requireContext(),R.color.warning))
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
        notification_checkin = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Create notification channel
            createNotificationChannel(channelId,channelName,importance)
            NotificationCompat.Builder(requireContext(), channelId)
        } else {
            NotificationCompat.Builder(requireContext())
        }.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            setContentTitle("Checkin Reminder")
            setContentText("Please kindly check-in today...")
        }.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}