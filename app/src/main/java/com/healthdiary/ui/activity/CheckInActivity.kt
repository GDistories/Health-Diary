package com.healthdiary.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.data.CheckInRecord
import com.healthdiary.databinding.ActivityCheckInBinding
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.repository.UserRepository
import com.healthdiary.viewmodel.CheckInRecordViewModel
import com.healthdiary.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.fragment_health.view.*
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class CheckInActivity : BaseActivity(), CalendarView.OnYearChangeListener,
    CalendarView.OnCalendarSelectListener, View.OnClickListener {

    private var mYear: Int = 0
    private var green = -0x66bf24db

    private val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
    private val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
    private val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))
    var dateToday = dayNow + monthNow + yearNow
    var googleSignInClient: GoogleSignInClient? = null

    private lateinit var binding: ActivityCheckInBinding

    var temperatureFilled: String?=null
    var heartRateFilled: String?=null
    var symptomFilled: String?=null
    var medicineFilled: String?=null

    var temp1: String?=null
    var temp2: String?=null

    private val recordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        val record: CheckInRecord =  CheckInRecord()
//        binding.checkInHistoryView.visibility = View.INVISIBLE

        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(StringUtils.getString(R.string.server_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnCheckInSubmit.setOnClickListener {
            record?.email = getUserEmail()
            record?.date = dateToday
            record?.temperature = binding.etTemperature.text.toString()
            record?.heartRate =  binding.etHeartRate.text.toString()
            record?.symptom = binding.etSymptom.text.toString()
            record?.medicine = binding.etMedicine.text.toString()
//            temperatureFilled = binding.etTemperature.text.toString()
//            heartRateFilled = binding.etHeartRate.text.toString()
//            symptomFilled = binding.etSymptom.text.toString()
//            medicineFilled = binding.etMedicine.text.toString()
            recordViewModel.updateRecord(record!!)
            startActivity(Intent(this, CheckInSuccessActivity::class.java))
        }
        binding.iconCheckInHistory.setOnClickListener {
            startActivity(Intent(this, CheckInHistoryActivity::class.java))
        }
//        binding.btnSymptom1.setOnClickListener {
//            temp1 = binding.etSymptom.text.toString()
//            temp2 = binding.btnSymptom1.text.toString()
//            binding.etSymptom.text = "temp2"
//        }
//        binding.btnSymptom2.setOnClickListener {
//            binding.etSymptom.text = binding.etSymptom.text + binding.btnSymptom2.text + ", "
//        }
//        binding.btnSymptom3.setOnClickListener {
//            binding.etSymptom.text = binding.etSymptom.text + binding.btnSymptom3.text + ", "
//        }
//        binding.btnSymptom4.setOnClickListener {
//            binding.etSymptom.text = binding.etSymptom.text + binding.btnSymptom4.text + ", "
//        }
//        binding.btnMed1.setOnClickListener {
//            binding.etMedicine.text = binding.etMedicine.text + binding.btnMed1.text + ", "
//        }

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

    private fun initData() {
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

    private fun calculateDayDifference(calendar: Calendar) {
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
    override fun onClick(v: View?) {}
    override fun onCalendarOutOfRange(calendar: Calendar?) {}

    private fun combineStrings(str1: String, str2: String){

    }

}