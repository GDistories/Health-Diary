package com.healthdiary.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import cn.addapp.pickers.listeners.OnItemPickListener
import cn.addapp.pickers.picker.DatePicker
import cn.addapp.pickers.picker.DatePicker.OnYearMonthDayPickListener
import cn.addapp.pickers.picker.SinglePicker
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityProfileBinding
import java.text.SimpleDateFormat

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
    private val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
    private val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))
    var yearSelected: String = yearNow
    var monthSelected: String = monthNow
    var daySelected: String = dayNow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.tvGender.setOnClickListener {
            showGenderPicker(it)
        }
        binding.tvBirthday.setOnClickListener {
            showYearMonthDayPicker(it)
        }
    }

    private fun showYearMonthDayPicker(view: View?) {
        val picker = DatePicker(this)
        picker.setGravity(Gravity.BOTTOM)
        picker.setAnimationStyle(R.style.Animation_CustomPopup)
        picker.setCanLoop(false)
        picker.setTopPadding(15)
        picker.setRangeStart(1900, 1, 1)
        picker.setRangeEnd(yearNow.toInt(), monthNow.toInt(), dayNow.toInt())
        picker.setSelectedItem(yearSelected.toInt(), monthSelected.toInt(), daySelected.toInt())
        picker.setWeightEnable(true)
        picker.isOuterLabelEnable = true
        picker.setLineVisible(true)
        picker.setOnDatePickListener(OnYearMonthDayPickListener { year, month, day ->
            binding.tvBirthday.text = "$year-$month-$day"
        })
        picker.setOnWheelListener(object : DatePicker.OnWheelListener {
            override fun onYearWheeled(index: Int, year: String) {
                yearSelected = year
                picker.setTitleText(year + "-" + picker.selectedMonth + "-" + picker.selectedDay)
                picker.setSelectedItem(yearSelected.toInt(), monthSelected.toInt(), daySelected.toInt())
            }

            override fun onMonthWheeled(index: Int, month: String) {
                monthSelected = month
                picker.setTitleText(picker.selectedYear + "-" + month + "-" + picker.selectedDay)
                picker.setSelectedItem(yearSelected.toInt(), monthSelected.toInt(), daySelected.toInt())
            }

            override fun onDayWheeled(index: Int, day: String) {
                picker.setTitleText(picker.selectedYear + "-" + picker.selectedMonth + "-" + day)
                daySelected = day
                picker.setSelectedItem(yearSelected.toInt(), monthSelected.toInt(), daySelected.toInt())
            }
        })
        picker.setLabel(" ", " ", " ")
        picker.show()
    }

    private fun showGenderPicker(view: View?) {
        val list = ArrayList<String?>()
        list.add(getString(R.string.male))
        list.add(getString(R.string.female))
        list.add(getString(R.string.other))
        val picker: SinglePicker<String?> = SinglePicker<String?>(this, list)
        picker.setGravity(Gravity.BOTTOM)
        picker.setAnimationStyle(R.style.Animation_CustomPopup)
        picker.setCanLoop(false)
        picker.setLineVisible(true)
        picker.setTextSize(18)
        picker.selectedIndex = 0
        picker.setItemWidth(100)
        picker.isOuterLabelEnable = true
        picker.setOnItemPickListener { _, item -> binding.tvGender.text = item }
        picker.show()
    }
}