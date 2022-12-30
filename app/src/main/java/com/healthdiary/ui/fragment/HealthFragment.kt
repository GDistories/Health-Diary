package com.healthdiary.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentHealthBinding
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.ui.activity.*
import com.healthdiary.viewmodel.CheckInRecordViewModel
import kotlinx.android.synthetic.main.fragment_health.*
import java.io.File
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


open class HealthFragment : BaseFragment(), CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener,
    View.OnClickListener {

    private var _binding: FragmentHealthBinding? = null
    private var mYear: Int = 0
    private val binding get() = _binding!!
    private var green = -0x66bf24db

    private val recordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }

    override fun onStart() {
        super.onStart()
        initView()
        if (!isLogin()) {
            binding.username.text = "(Unknown User)"
            ToastUtils.showShort(getString(R.string.please_login))
        }
        else{
            initData()
        }
        if (isLogin()) {
            getUserPhoto(context!!.cacheDir.absolutePath + "/" + getUserEmail() + ".jpg")
        }
        else
        {
            binding.ivAvatar.setImageResource(R.drawable.default_profile_pic)
        }

        binding.iconEdit.setOnClickListener {
            startActivity(Intent(activity, ProfileActivity::class.java))
        }
        binding.iconCheckIn.setOnClickListener {
            startActivity(Intent(activity, CheckInActivity::class.java))
        }
        binding.iconScore.setOnClickListener {
            startActivity(Intent(activity, ScoreActivity::class.java))
        }
        binding.iconTracker.setOnClickListener {
            startActivity(Intent(activity, TrackerActivity::class.java))
        }
        binding.iconTips.setOnClickListener {
            startActivity(Intent(activity, TipsActivity::class.java))
        }
        binding.iconDashboard.setOnClickListener {
            startActivity(Intent(activity, DashboardActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun initView() {

        binding.flCurrent.setOnClickListener {
            binding.calendarView.scrollToCurrent()
        }

        binding.calendarView.setOnYearChangeListener(this)
        binding.calendarView.setOnCalendarSelectListener(this)
        binding.tvYear.text = binding.calendarView.curYear.toString()
        mYear = binding.calendarView.curYear
        val g1 = DecimalFormat("00")
        binding.tvMonthDay.text = g1.format(binding.calendarView.curMonth.toLong()) + "-" + g1.format(
            binding.calendarView.curDay.toLong()
        )
        binding.tvLunar.text = getString(R.string.today)
        binding.tvCurrentDay.text = binding.calendarView.curDay.toString()
    }

    protected open fun initData() {
        val year: Int = binding.calendarView.curYear
        val month: Int = binding.calendarView.curMonth
        val map: MutableMap<String, Calendar> = HashMap()

        recordViewModel.checkAllRecord(getUserEmail().toString()).observe(this){
            var checkId = it
            var dateStr = ""

            if(checkId != "NoCheckInResult"){
                recordViewModel.getRecord(checkId).observe(this){
                    var record = it
                    dateStr = record.date.toString()
                    var yearRecord:Int = dateStr.substring(0,4).toInt()
                    var monthRecord:Int = dateStr.substring(4,6).toInt()
                    var dayRecord:Int = dateStr.substring(6,8).toInt()

                    map[getSchemeCalendar(yearRecord, monthRecord, dayRecord, green).toString()] =
                        getSchemeCalendar(yearRecord, monthRecord, dayRecord, green)

                    binding.calendarView.setSchemeDate(map)
                }
            }

        }
        binding.calendarView.setSchemeDate(map)
    }

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color
        calendar.scheme = ""
        return calendar
    }
    override fun onCalendarSelect(calendar: Calendar, isClick: Boolean) {
        startActivity(Intent(activity, CheckInActivity::class.java))

        binding.tvLunar.visibility = View.VISIBLE
        binding.tvYear.visibility = View.VISIBLE
        val g1 = DecimalFormat("00")
        binding.tvMonthDay.text = g1.format(calendar.month.toLong()) + "-" + g1.format(calendar.day.toLong())
        binding.tvYear.text = calendar.year.toString()
        binding.tvLunar.text = calendar.lunar
        mYear = calendar.year
        calculateDayDifference(calendar)

    }

    override fun onYearChange(year: Int) {
        binding.tvMonthDay.text = year.toString()
    }

    open fun calculateDayDifference(calendar: Calendar) {
        @SuppressLint("SimpleDateFormat") val df: DateFormat = SimpleDateFormat("yyyyMMdd")
        try {
            val d1 = df.parse(calendar.toString())
            val today = df.parse(df.format(Date()))
            if (d1 != null && today != null) {
                val diff = (d1.time - today.time).toDouble()
                val days = diff / (1000 * 60 * 60 * 24)
                Log.e("TAG", days.toString())
                if (days > 0) {
                    binding.tvLunar.text = days.toInt().toString() + " " + getString(R.string.days_later)
                } else if (days < 0) {
                    binding.tvLunar.text = abs(days.toInt()).toString() + " " + getString(R.string.days_ago)
                } else {
                    binding.tvLunar.text = getString(R.string.today)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUserPhoto(savePath: String?) {
        val file = File(savePath!!)
        if (!file.exists()) {
            binding.ivAvatar.setImageResource(R.drawable.default_profile_pic)
        } else {
            val bitmap: Bitmap? = readBitmap(context, savePath)
            binding.ivAvatar.setImageBitmap(bitmap)
        }
    }

    private fun readBitmap(ct: Context?, savePath: String?): Bitmap? {
        val bitmap: Bitmap
        return try {
            val filePic = File(savePath!!)
            if (!filePic.exists()) {
                return null
            }
            bitmap = BitmapFactory.decodeFile(savePath)
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    override fun onClick(v: View?) {}
    override fun onCalendarOutOfRange(calendar: Calendar?) {}
}

