package com.healthdiary.ui.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
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
import kotlin.reflect.jvm.internal.impl.types.TypeCheckerState.SupertypesPolicy.None

class CheckInActivity : BaseActivity(), CalendarView.OnYearChangeListener,
    CalendarView.OnCalendarSelectListener, View.OnClickListener {

    private var mYear: Int = 0
    private var green = -0x66bf24db

    // To get string of DATE TODAY
    var dateToday = getToday()
    var googleSignInClient: GoogleSignInClient? = null

    private lateinit var binding: ActivityCheckInBinding

    private val recordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val record: CheckInRecord =  CheckInRecord()

        super.onCreate(savedInstanceState)
        if (!isLogin()) {
            ToastUtils.showShort(getString(R.string.please_login))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

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
            record.email = getUserEmail()
            record.date = dateToday
            record.temperature = binding.etTemperature.text.toString() + "℃"
            record.heartRate =  binding.etHeartRate.text.toString() + " Bpm"
            record.symptom = binding.etSymptom.text.toString()
            record.medicine = binding.etMedicine.text.toString()

            recordViewModel.checkRecord(getUserEmail().toString(),dateToday).observe(this){
                var checkId = it
                if(checkId != null){
                    if (checkId == "NotCheckInYet")
                    {
                        recordViewModel.addRecord(record)
                    }
                    else
                    {
                        recordViewModel.updateRecord(record, checkId)
                    }
                }
                startActivity(Intent(this, CheckInSuccessActivity::class.java))
            }
        }

    }

    override fun onStart() {
        super.onStart()
        binding.checkInView.visibility = View.VISIBLE
        binding.checkInHistoryView.visibility = View.GONE

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

        recordViewModel.checkAllRecord(getUserEmail().toString()).observe(this){
            var checkId = it
            var dateStr = ""

            if (checkId != "NoCheckInResult"){
                recordViewModel.getRecord(checkId).observe(this){ it1 ->
                    var record = it1
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

        val g1 = DecimalFormat("00")
        val dateString = calendar.year.toString() + g1.format(calendar.month.toLong()) + g1.format(calendar.day.toLong())
        changeView(dateString)
    }
    override fun onClick(v: View?) {}
    override fun onCalendarOutOfRange(calendar: Calendar?) {}

    fun changeView(dateString: String){
        if(binding.tvLunar.text == getString(R.string.today)){
            Log.e(ContentValues.TAG, "Mode 1")
            binding.checkInView.visibility = View.VISIBLE
            binding.checkInHistoryView.visibility = View.GONE
            binding.checkInNoHistoryView.visibility = View.GONE
        }
        else {
            //  Checkrecord() - by its (email, date)
            recordViewModel.checkRecord(getUserEmail().toString(),dateString).observe(this){
                var checkId = it
                if(checkId != null){
                    if(checkId == "NotCheckInYet"){
                        Log.e(ContentValues.TAG, "Mode 2")
                        binding.checkInView.visibility = View.GONE
                        binding.checkInHistoryView.visibility = View.GONE
                        binding.checkInNoHistoryView.visibility = View.VISIBLE
                    }
                    else
                    {
                        Log.e(ContentValues.TAG, "Mode 3")
                        binding.checkInView.visibility = View.GONE
                        binding.checkInHistoryView.visibility = View.VISIBLE
                        binding.checkInNoHistoryView.visibility = View.GONE
                        //  getRecord() - find record by its id
                        recordViewModel.getRecord(checkId).observe(this){ it1 ->
                            var record = it1
                            // update the texts
                            if(record.temperature != null && record.temperature != ""){
                                binding.tvHistoryTemperature.text = record.temperature
                            }
                            if(record.heartRate != null && record.heartRate != ""){
                                binding.tvHistoryHeartRate.text = record.heartRate
                            }
                            if(record.symptom != null && record.symptom != ""){
                                binding.tvHistorySymptom.text = record.symptom
                            }
                            if(record.medicine != null && record.medicine != ""){
                                binding.tvHistoryMedicine.text = record.medicine
                            }
                        }
                    }

                }
            }

        }

    }


}