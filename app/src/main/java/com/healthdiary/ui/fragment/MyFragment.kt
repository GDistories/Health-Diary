package com.healthdiary.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.base.BaseFragment
import com.healthdiary.R
import com.healthdiary.databinding.FragmentMyBinding


class MyFragment : BaseFragment() {
    private var _binding: FragmentMyBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}