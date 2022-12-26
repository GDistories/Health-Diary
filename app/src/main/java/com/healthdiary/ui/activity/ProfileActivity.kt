package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import cn.addapp.pickers.picker.DatePicker
import cn.addapp.pickers.picker.DatePicker.OnYearMonthDayPickListener
import cn.addapp.pickers.picker.SinglePicker
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityProfileBinding
import com.healthdiary.repository.AuthRepository
import com.healthdiary.viewmodel.AuthViewModel
import java.text.SimpleDateFormat

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
    private val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
    private val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))

    var name: String? = null
    var gender: String? = null
    var birthday: String? = null
    var phoneNum: String? = null

    var yearSelected: String = yearNow
    var monthSelected: String = monthNow
    var daySelected: String = dayNow
    var googleSignInClient: GoogleSignInClient? = null

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel.Provider(AuthRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        if (!isLogin()){
            ToastUtils.showShort(getString(R.string.please_login))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(StringUtils.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val user = getUserInfo()

        binding.tvEmail.text = user?.email ?: ""
        binding.etName.setText(user?.name ?: "")
        binding.tvGender.text = user?.gender ?: ""
        binding.tvBirthday.text = user?.birthday ?: ""
        binding.etPhoneNumber.setText(user?.phone ?: "")

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvGender.setOnClickListener {
            showGenderPicker(it)
        }

        binding.tvBirthday.setOnClickListener {
            showYearMonthDayPicker(it)
        }

        binding.btnSubmit.setOnClickListener {

            finish()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            googleSignInClient?.signOut()
            ToastUtils.showShort(getString(R.string.logout_success))
            finish()
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
            birthday = "$year-$month-$day"
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
        picker.setOnItemPickListener { _, item ->
            binding.tvGender.text = item
            gender = item
        }
        picker.show()
    }
}