package org.hywel.kotlintest.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fanfou_item_layout.view.*
import org.hywel.kotlintest.R
import org.hywel.kotlintest.data.MainRVData
import org.hywel.kotlintest.interf.OnRecyclerViewOnClickListener

/**
 * Created by hywel on 2017/7/31.
 */
class MainRVAdapter(val context: Context, val list: List<MainRVData>) : RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var mListener: OnRecyclerViewOnClickListener? = null

    var layoutPosition = 0

    val tfAuthorFromAsset = Typeface.createFromAsset(context.assets, "fonts/Luminari.ttf")!!
    val tfContentFromAsset = Typeface.createFromAsset(context.assets, "fonts/Bradley Hand Bold.ttf")!!
    //        val tfTimeFromAsset = Typeface.createFromAsset(assets, "fonts/tesla.ttf")
    val tfTimeFromAsset = Typeface.createFromAsset(context.assets, "fonts/Brush Script.ttf")!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        layoutPosition = position

        val item = list[position]

        if (!item.imgUrl.isNullOrEmpty()) {
            holder.itemView.iv_main.visibility = View.VISIBLE
            Glide.with(context).load(item.avatarUrl).into(holder.itemView.iv_main)
        } else {
            holder.itemView.iv_main.visibility = View.INVISIBLE
        }

        holder.itemView.tv_author.text = item.author
        holder.itemView.tv_author.typeface = tfAuthorFromAsset

        holder.itemView.tv_content.text = android.text.Html.fromHtml(item.content).toString()
        holder.itemView.tv_content.typeface = tfContentFromAsset

        holder.itemView.tv_time.text = item.time
        holder.itemView.tv_time.typeface = tfTimeFromAsset
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView: View = inflater.inflate(R.layout.fanfou_item_layout, parent, false)
        return ViewHolder(itemView, mListener!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItemClickListener(listener: OnRecyclerViewOnClickListener) {
        this.mListener = listener
    }

    inner class ViewHolder(itemView: View, internal var listener: OnRecyclerViewOnClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.OnItemClick(p0!!, layoutPosition)
        }
    }
}