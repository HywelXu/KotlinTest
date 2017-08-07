package org.hywel.kotlintest.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.activity_main.*
import org.hywel.kotlintest.KotApplication
import org.hywel.kotlintest.R
import org.hywel.kotlintest.adapter.MainRVAdapter
import org.hywel.kotlintest.data.MainRVData
import org.hywel.kotlintest.interf.OnRecyclerViewOnClickListener
import org.hywel.kotlintest.utils.DataServer
import org.hywel.kotlintest.utils.GlideImageLoader

class MainActivity : AppCompatActivity() {

    private var mMainRVData: MainRVData? = null

    private var mMainRVAdapter: MainRVAdapter? = null

    private var mMainDataList = ArrayList<MainRVData>()

    val kotApplication = KotApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setTitle()
        setBanner()
        setRecyclerView()
    }

    /**
     * 设置标题
     */
    private fun setTitle() {
        val mainTitle = resources.getText(R.string.app_main_page_label_text)

        main_collapsing_toolbar_layout.title = mainTitle
        main_collapsing_toolbar_layout.setCollapsedTitleTypeface(kotApplication.mLuminariTypeface)
    }

    /**
     * 设置 RecyclerView
     */
    private fun setRecyclerView() {
        getData()

        mMainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        mMainRVAdapter = MainRVAdapter(this, mMainDataList)
        mMainRecyclerView.adapter = mMainRVAdapter
        mMainRVAdapter!!.setItemClickListener(object : OnRecyclerViewOnClickListener {
            override fun OnItemClick(v: View, position: Int) {
                jumpDetail(position)
                //                Toast.makeText(this@MainActivity, "Name: " + mMainDataList[position].author, Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 设置焦点图
     */
    private fun setBanner() {
        val dataServer = DataServer()
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(dataServer.BANNER_TITLES.asList())
        //设置图片集合
        banner.setImages(dataServer.BANNER_IMAGES.asList())
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Accordion)
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(2000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

    /**
     * 跳转到详情页
     */
    private fun jumpDetail(position: Int) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        val item = mMainDataList[position]

        intent.putExtra("avatarUrl", item.avatarUrl)
        intent.putExtra("author", item.author)
        intent.putExtra("content", item.content)
        intent.putExtra("imgUrl", item.imgUrl)
        intent.putExtra("time", item.time)

        startActivity(intent)
    }

    fun getData() {

        val author = resources.getText(R.string.author_text)
        val content = resources.getText(R.string.large_text)
        val time = resources.getText(R.string.time_text)

        val dataServer = DataServer()
        val imgListSize = dataServer.IMG.size

        (0..40).forEach { i ->
            mMainRVData = MainRVData(dataServer.IMG[i % 40], "$author", "content$i: $content", "$time", dataServer.IMG[i % 40])
//            mMainRVData = MainRVData("$author", "content$i: $content", "$time")
            mMainDataList.add(mMainRVData!!)
        }
    }
}
