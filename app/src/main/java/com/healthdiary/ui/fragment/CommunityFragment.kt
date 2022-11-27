package com.healthdiary.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentCommunityBinding
import com.healthdiary.codepalace.chatbot.ui.ChatActivity
import com.healthdiary.ui.activity.DoctorInfoActivity
import com.healthdiary.ui.activity.DoctorListActivity


class CommunityFragment : BaseFragment() {
    private var _binding: FragmentCommunityBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}