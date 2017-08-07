package org.hywel.kotlintest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
import org.hywel.kotlintest.KotApplication
import org.hywel.kotlintest.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val data = intent.extras
        val author = data.getString("author")
        val content = data.getString("content")
        val time = data.getString("time")
//        val avatarUrl = data.getString("avatarUrl")
        val imgUrl = data.getString("imgUrl")

        val kotApplication = KotApplication

        //设置标题
        collapsing_toolbar_layout.title = author
        collapsing_toolbar_layout.setCollapsedTitleTypeface(kotApplication.mLuminariTypeface)

        //设置内容
//        val largeText = resources.getText(R.string.large_text1)
        detail_tv_content.text = content
        detail_tv_content.typeface = kotApplication.mBradleyTypeface

        //设置时间
        detail_tv_time.text = time
        detail_tv_time.typeface = kotApplication.mBrushTypeface

        tv_back.setOnClickListener { onBackPressed() }

//        val screenWidth = AndroidTools.getScreenWidth(this)
//        val imgWidth: Int = screenWidth
//        val imgHeight: Int = screenWidth * 9 / 16

//        iv_main.layoutParams.width = imgWidth
//        iv_main.layoutParams.height = imgHeight
        Glide.with(this).load(imgUrl).into(detail_iv_main)
    }
}
