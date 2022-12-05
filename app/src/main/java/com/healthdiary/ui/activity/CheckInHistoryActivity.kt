package com.healthdiary.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityCheckInHistoryBinding
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

open class CheckInHistoryActivity : BaseActivity(), CalendarView.OnYearChangeListener,
    CalendarView.OnCalendarSelectListener {
    private lateinit var binding: ActivityCheckInHistoryBinding

    private var mYear: Int = 0
    private var green = -0x66bf24db

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        initView()
        initData()
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
        map[getSchemeCalendar(year, month, 3, green).toString()] =
            getSchemeCalendar(year, month, 3, green)
        map[getSchemeCalendar(year, month, 6, green).toString()] =
            getSchemeCalendar(year, month, 6, green)
        map[getSchemeCalendar(year, month, 9, green).toString()] =
            getSchemeCalendar(year, month, 9, green)
        map[getSchemeCalendar(year, month, 13, green).toString()] =
            getSchemeCalendar(year, month, 13, green)
        map[getSchemeCalendar(year, month, 14, green).toString()] =
            getSchemeCalendar(year, month, 14, green)
        map[getSchemeCalendar(year, month, 15, green).toString()] =
            getSchemeCalendar(year, month, 15, green)
        map[getSchemeCalendar(year, month, 18, green).toString()] =
            getSchemeCalendar(year, month, 18, green)
        map[getSchemeCalendar(year, month, 25, green).toString()] =
            getSchemeCalendar(year, month, 25, green)
        map[getSchemeCalendar(year, month, 27, green).toString()] =
            getSchemeCalendar(year, month, 27, green)
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

    override fun onCalendarOutOfRange(calendar: Calendar?) {}
}