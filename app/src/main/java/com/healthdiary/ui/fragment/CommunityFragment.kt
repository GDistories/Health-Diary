package com.healthdiary.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentCommunityBinding
import com.healthdiary.codepalace.chatbot.ui.ChatActivity
import com.healthdiary.ui.activity.DoctorInfoActivity
import com.healthdiary.ui.activity.DoctorListActivity
import java.io.File


class CommunityFragment : BaseFragment() {
    private var _binding: FragmentCommunityBinding? = null

    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()

        binding.categoryDoctor.setOnClickListener {
            startActivity(Intent(activity, DoctorListActivity::class.java))
        }

        binding.communityTop1.doctorPersonalInfoBtn.setOnClickListener {
            startActivity(Intent(activity, DoctorInfoActivity::class.java))
        }
        binding.communityTop2.doctorPersonalInfoBtn.setOnClickListener {
            startActivity(Intent(activity, DoctorInfoActivity::class.java))
        }
        binding.communityTop1.doctorChat.setOnClickListener {
            startActivity(Intent(activity, ChatActivity::class.java))
        }
        binding.communityTop2.doctorChat.setOnClickListener {
            startActivity(Intent(activity, ChatActivity::class.java))
        }
        if (isLogin()) {
            getUserPhoto(context!!.cacheDir.absolutePath + "/" + getUserEmail() + ".jpg")
        }
        else
        {
            binding.ivProfilePhoto.setImageResource(R.drawable.ic_user_profile_photo_unlogin)
        }

    }

    private fun getUserPhoto(savePath: String?) {
        val file = File(savePath!!)
        if (!file.exists()) {
            binding.ivProfilePhoto.setImageResource(R.drawable.ic_user_profile_photo_unlogin)
        } else {
            val bitmap: Bitmap? = readBitmap(context, savePath)
            binding.ivProfilePhoto.setImageBitmap(bitmap)
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}