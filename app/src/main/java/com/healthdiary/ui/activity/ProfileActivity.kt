package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import cn.addapp.pickers.picker.DatePicker
import cn.addapp.pickers.picker.DatePicker.OnYearMonthDayPickListener
import cn.addapp.pickers.picker.NumberPicker
import cn.addapp.pickers.picker.NumberPicker.OnNumberPickListener
import cn.addapp.pickers.picker.SinglePicker
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.data.User
import com.healthdiary.databinding.ActivityProfileBinding
import com.healthdiary.repository.AuthRepository
import com.healthdiary.repository.UserRepository
import com.healthdiary.viewmodel.AuthViewModel
import com.healthdiary.viewmodel.UserViewModel
import java.text.SimpleDateFormat

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val yearNow = TimeUtils.getNowString(SimpleDateFormat("yyyy"))
    private val monthNow = TimeUtils.getNowString(SimpleDateFormat("MM"))
    private val dayNow = TimeUtils.getNowString(SimpleDateFormat("dd"))

    var gender: String? = null
    var birthday: String? = null
    var height: Number = 0
    var weight: Number = 0

    var yearSelected: String = yearNow
    var monthSelected: String = monthNow
    var daySelected: String = dayNow
    var googleSignInClient: GoogleSignInClient? = null

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.Provider(AuthRepository.repository)
    }
    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Provider(UserRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()
        var user: User? = null

        if (!isLogin()) {
            ToastUtils.showShort(getString(R.string.please_login))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        else{
            userViewModel.getUser(getUserEmail()!!).observe(this){
                user = it
                updateUI(user)
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(StringUtils.getString(R.string.server_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvGender.setOnClickListener {
            showGenderPicker(it)
        }

        binding.tvBirthday.setOnClickListener {
            showYearMonthDayPicker(it)
        }

        binding.tvHeight.setOnClickListener {
            showHeightPicker(it)
        }

        binding.tvWeight.setOnClickListener {
            showWeightPicker(it)
        }

        binding.btnSubmit.setOnClickListener {
            user?.name = binding.etName.text.toString()
            user?.gender = binding.tvGender.text.toString()
            user?.birthday = binding.tvBirthday.text.toString()
            user?.phone = binding.etPhoneNumber.text.toString()
            user?.height = height
            user?.weight = weight
            userViewModel.updateUser(user!!)
            finish()
        }
        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
            googleSignInClient?.signOut()
            ToastUtils.showShort(getString(R.string.logout_success))
            finish()
        }
    }

    private fun updateUI(user: User?){
        binding.tvEmail.text = ""
        binding.etName.setText("")
        binding.tvGender.text = ""
        binding.tvBirthday.text = ""
        binding.etPhoneNumber.setText("")

        if (user?.email != "null" || user.email?.isEmpty() == true) {
            binding.tvEmail.text = user?.email
        }
        if (user?.name != "null" || user.name?.isEmpty() == true) {
            binding.etName.setText(user?.name)
        }
        if (user?.gender != "null" || user.gender?.isEmpty() == true) {
            binding.tvGender.text = user?.gender
        }
        if (user?.birthday != "null" || user.birthday?.isEmpty() == true) {
            binding.tvBirthday.text = user?.birthday
        }
        if (user?.phone != "null" || user.phone?.isEmpty() == true) {
            binding.etPhoneNumber.setText(user?.phone)
        }
        if (user?.height != 0 && user?.height != null) {
            height = user.height!!
            binding.tvHeight.text = user.height.toString() + " " + getString(R.string.cm)
        }
        if (user?.weight != 0 && user?.height != null) {
            weight = user.weight!!
            binding.tvWeight.text = user.weight.toString() + " " + getString(R.string.kg)
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

    private fun showHeightPicker(view: View?) {
        val picker = NumberPicker(this)
        picker.setGravity(Gravity.BOTTOM)
        picker.setAnimationStyle(R.style.Animation_CustomPopup)
        picker.setCanLoop(false)
        picker.setLineVisible(true)
        picker.setTextSize(18)
        picker.setItemWidth(100)
        picker.selectedIndex = 0
        picker.isOuterLabelEnable = true
        picker.setRange(120, 220, 1) //数字范围
        picker.setSelectedItem(170)
        picker.setLabel(getString(R.string.cm))
        picker.setOnNumberPickListener(object : OnNumberPickListener() {
            override fun onNumberPicked(index: Int, item: Number) {
                binding.tvHeight.text = item.toString() + " " + getString(R.string.cm)
                height = item
            }
        })
        picker.show()
    }

    private fun showWeightPicker(view: View?) {
        val picker = NumberPicker(this)
        picker.setGravity(Gravity.BOTTOM)
        picker.setAnimationStyle(R.style.Animation_CustomPopup)
        picker.setCanLoop(false)
        picker.setLineVisible(true)
        picker.setTextSize(18)
        picker.setItemWidth(100)
        picker.selectedIndex = 0
        picker.isOuterLabelEnable = true
        picker.setRange(40, 150, 1) //数字范围
        picker.setSelectedItem(60)
        picker.setLabel(getString(R.string.kg))
        picker.setOnNumberPickListener(object : OnNumberPickListener() {
            override fun onNumberPicked(index: Int, item: Number) {
                binding.tvWeight.text = item.toString() + " " +getString(R.string.kg)
                weight = item
            }
        })
        picker.show()
    }
}