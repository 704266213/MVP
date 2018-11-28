package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvp.R
import com.mvp.adapter.listener.IBinderViewHolder

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2018/11/28 9:16
 * 修改人：admin
 * 修改时间：2018/11/28 9:16
 * 修改备注：
 * @version
 */
class BannerBinderViewHolder : IBinderViewHolder<BannerBinderViewHolder.BannerViewHolder> {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val itemVew = LayoutInflater.from(parent!!.context).inflate(R.layout.banner_layout, parent, false)
        return BannerViewHolder(itemVew)
    }

    override fun onBindViewHolder(holder: BannerViewHolder?, position: Int) {

    }


    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}