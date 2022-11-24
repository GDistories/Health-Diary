package com.healthdiary.ui.activity


import android.net.Uri
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityNewsContentBinding
import kotlinx.android.synthetic.main.activity_news_content.*

class NewsContentActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        val onlineUri = Uri.parse("https://www.shutterstock.com/shutterstock/videos/1060058060/preview/stock-footage-asia-businessmen-and-businesswomen-meeting-brainstorming-ideas-about-new-paperwork-colleagues.webm")

        videoView.setMediaController(mediaController)
        videoView.setVideoURI(onlineUri)
        videoView.requestFocus()
        videoView.start()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}