package com.healthdiary.ui.activity

import android.os.Bundle
import cn.jzvd.Jzvd
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityNewsContentBinding

class NewsContentActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        val onlineUrl = "https://www.shutterstock.com/shutterstock/videos/1060058060/preview/stock-footage-asia-businessmen-and-businesswomen-meeting-brainstorming-ideas-about-new-paperwork-colleagues.webm"

        //初始化视频播放器的视频链接和标题
        binding.jzvdVideo.setUp(onlineUrl, "视频标题")
        //设置视频播放器的封面，视频默认是暂停状态
        binding.jzvdVideo.posterImageView.setImageResource(R.drawable.image_simple_watch)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}