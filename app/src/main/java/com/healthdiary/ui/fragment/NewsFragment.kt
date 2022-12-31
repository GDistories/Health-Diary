package com.healthdiary.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.healthdiary.R
import com.healthdiary.base.BaseFragment
import com.healthdiary.databinding.FragmentNewsBinding
import com.healthdiary.ui.activity.*


class NewsFragment : BaseFragment() {
    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        binding.newsAll.background = resources.getDrawable(R.drawable.btn_border_selected)

        binding.newsAll.setOnClickListener {
            binding.news1.visibility = View.VISIBLE
            binding.news2.visibility = View.VISIBLE
            binding.news3.visibility = View.VISIBLE
            binding.news4.visibility = View.VISIBLE
            recover()
            binding.newsAll.background = resources.getDrawable(R.drawable.btn_border_selected)
        }
        binding.newsReport.setOnClickListener {
            binding.news1.visibility = View.VISIBLE
            binding.news2.visibility = View.GONE
            binding.news3.visibility = View.GONE
            binding.news4.visibility = View.GONE
            recover()
            binding.newsReport.background = resources.getDrawable(R.drawable.btn_border_selected)
        }
        binding.newsChroniccare.setOnClickListener {
            binding.news1.visibility = View.GONE
            binding.news2.visibility = View.GONE
            binding.news3.visibility = View.GONE
            binding.news4.visibility = View.GONE
            recover()
            binding.newsChroniccare.background = resources.getDrawable(R.drawable.btn_border_selected)
        }
        binding.newsFitness.setOnClickListener {
            binding.news1.visibility = View.GONE
            binding.news2.visibility = View.GONE
            binding.news3.visibility = View.GONE
            binding.news4.visibility = View.GONE
            recover()
            binding.newsFitness.background = resources.getDrawable(R.drawable.btn_border_selected)
        }
        binding.newsDiet.setOnClickListener {
            binding.news1.visibility = View.GONE
            binding.news2.visibility = View.GONE
            binding.news3.visibility = View.VISIBLE
            binding.news4.visibility = View.VISIBLE
            recover()
            binding.newsDiet.background = resources.getDrawable(R.drawable.btn_border_selected)
        }

        binding.news1.setOnClickListener {
            startActivity(Intent(activity,NewsContentActivity::class.java))
        }
        binding.news2.setOnClickListener {
            startActivity(Intent(activity,NewsWebContentActivity::class.java))
        }
    }

    fun recover() {
        binding.newsAll.background = resources.getDrawable(R.drawable.btn_border)
        binding.newsReport.background = resources.getDrawable(R.drawable.btn_border)
        binding.newsChroniccare.background = resources.getDrawable(R.drawable.btn_border)
        binding.newsFitness.background = resources.getDrawable(R.drawable.btn_border)
        binding.newsDiet.background = resources.getDrawable(R.drawable.btn_border)
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