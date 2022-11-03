package com.healthdiary.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentMyBinding
import com.healthdiary.ui.activity.AboutActivity


class MyFragment : BaseFragment() {
    private var _binding: FragmentMyBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.update.setOnClickListener {
            Toast.makeText(activity, R.string.searching_update, Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, R.string.no_update, Toast.LENGTH_SHORT).show()
        }
        binding.about.setOnClickListener { startActivity(Intent(activity, AboutActivity::class.java)) }
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
}