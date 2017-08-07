package org.hywel.kotlintest.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*
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

        //设置字体
        val tfAuthorFromAsset = Typeface.createFromAsset(assets, "fonts/Luminari.ttf")
        val tfContentFromAsset = Typeface.createFromAsset(assets, "fonts/Bradley Hand Bold.ttf")
//        val tfTimeFromAsset = Typeface.createFromAsset(assets, "fonts/tesla.ttf")
        val tfTimeFromAsset = Typeface.createFromAsset(assets, "fonts/Brush Script.ttf")

        collapsing_toolbar_layout.title = author
        collapsing_toolbar_layout.setCollapsedTitleTypeface(tfAuthorFromAsset)
//        detail_tv_author.text = author
//        detail_tv_author.typeface = tfAuthorFromAsset

//        val largeText = resources.getText(R.string.large_text1)
        detail_tv_content.text = content
        detail_tv_content.typeface = tfContentFromAsset

        detail_tv_time.text = time
        detail_tv_time.typeface = tfTimeFromAsset

        tv_back.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })
//        val screenWidth = AndroidTools.getScreenWidth(this)
//        val imgWidth: Int = screenWidth
//        val imgHeight: Int = screenWidth * 9 / 16

//        iv_main.layoutParams.width = imgWidth
//        iv_main.layoutParams.height = imgHeight
        Glide.with(this).load(imgUrl).into(detail_iv_main)
    }
}
