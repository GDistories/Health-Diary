package com.healthdiary.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.data.User
import com.healthdiary.databinding.FragmentMyBinding
import com.healthdiary.repository.UserRepository
import com.healthdiary.ui.activity.*
import com.healthdiary.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class MyFragment : BaseFragment() {
    private var _binding: FragmentMyBinding? = null
    var user: User? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.Provider(UserRepository.repository)
    }

    override fun onStart() {
        super.onStart()
        if (isLogin()) {
            // User is signed in
            userViewModel.getUser(getUserEmail()!!).observe(this){
                user = it
                if (user != null) {
                    LogUtils.e(user!!.name)
                    LogUtils.e(user!!.name)
                    if (user?.name == "null" || user!!.name?.isEmpty() == true)
                    {
                        binding.username.text = getString(R.string.anonymous)
                    }else
                    {
                        binding.username.text = user!!.name
                    }
                }
            }

            getUserPhoto(context!!.cacheDir.absolutePath + "/" + getUserEmail() + ".jpg")

            val calendar = Calendar.getInstance()

            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> binding.description.text = getString(R.string.good_morning)
                in 12..17 -> binding.description.text = getString(R.string.good_afternoon)
                in 18..23 -> binding.description.text = getString(R.string.good_evening)
            }

        } else {
            binding.ivProfileImage.setImageResource(R.drawable.default_profile_pic)
            binding.username.text = getString(R.string.guest)
            binding.description.text = getString(R.string.guest_description)
        }

        binding.profile.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
        binding.setting.setOnClickListener {
            startActivity(Intent(context, SettingActivity::class.java))
        }
        binding.help.setOnClickListener {

            startActivity(Intent(context, HelpActivity::class.java))
        }
        binding.devices.setOnClickListener {
            startActivity(Intent(context, DevicesActivity::class.java))
        }
        binding.update.setOnClickListener {
            Toast.makeText(activity, R.string.searching_update, Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, R.string.no_update, Toast.LENGTH_SHORT).show()
        }
        binding.about.setOnClickListener {
            startActivity(Intent(activity, AboutActivity::class.java))
        }

        binding.ivProfileImage.setOnClickListener {
            if (isLogin()) {
                openAlbum()
            } else {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openAlbum() {
        val intent2 = Intent("android.intent.action.PICK")
        intent2.type = "image/*"
        startActivityForResult(intent2, 1)
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

    private fun getUserPhoto(savePath: String?) {
        val file = File(savePath!!)
        if (!file.exists()) {
            binding.ivProfileImage.setImageResource(R.drawable.default_profile_pic)
        } else {
            val bitmap: Bitmap? = readBitmap(context, savePath)
            binding.ivProfileImage.setImageBitmap(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (data != null) {
                val uri = data.data
                onPhoto(uri, 300, 300)
            }
            2 -> if (data != null) {
                val extras = data.extras
                val bitmap = extras!!["data"] as Bitmap?
                if (bitmap != null) {
                    saveBitmap(
                        bitmap,
                        activity,
                        context!!.cacheDir.absolutePath + "/" + getUserEmail() + ".jpg"
                    )
                    binding.ivProfileImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun onPhoto(uri: Uri?, outputX: Int, outputY: Int) {
        var intent: Intent? = null
        intent = Intent("com.android.camera.action.CROP")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", outputX)
        intent.putExtra("outputY", outputY)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", true)
        intent.putExtra("circleCrop", true)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        startActivityForResult(intent, 2)
    }

    private fun saveBitmap(bitmap: Bitmap, ct: Context?, savePath: String?) {
        val filePic: File
        try {
            filePic = File(savePath!!)
            if (!filePic.exists()) {
                filePic.parentFile?.mkdirs()
                filePic.createNewFile()
            }
            val fos = FileOutputStream(filePic)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            return
        }
    }
}