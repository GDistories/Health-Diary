package com.healthdiary.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentNewsBinding
import com.healthdiary.ui.activity.*


class NewsFragment : BaseFragment() {
    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        binding.news1.setOnClickListener {
            startActivity(Intent(activity,NewsContentActivity::class.java))
        }
        binding.news2.setOnClickListener {
            startActivity(Intent(activity,NewsWebContentActivity::class.java))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}