package com.healthdiary.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.base.BaseFragment
import com.healthdiary.R
import com.healthdiary.databinding.FragmentHomeBinding
import com.healthdiary.ui.activity.*


class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.news1.setOnClickListener {
            startActivity(Intent(activity, NewsContentActivity::class.java))
        }
        binding.checkInRecord.setOnClickListener{
            startActivity(Intent(activity, CheckInHistoryActivity::class.java))
        }
        binding.healthScore.setOnClickListener{
            startActivity(Intent(activity, ScoreActivity::class.java))
        }
        binding.healthTips.setOnClickListener{
            startActivity(Intent(activity, TipsActivity::class.java))
        }
        binding.trackData.setOnClickListener{
            startActivity(Intent(activity, TrackerActivity::class.java))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}