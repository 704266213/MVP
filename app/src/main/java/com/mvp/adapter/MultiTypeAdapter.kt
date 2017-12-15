package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/15 16:07
 * 修改人：admin
 * 修改时间：2017/12/15 16:07
 * 修改备注：
 * @version
 */
class MultiTypeAdapter<in T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val listData = mutableListOf<T>()


    override fun getItemViewType(position: Int): Int {
       val entity = listData[position]

    }

    override fun getItemCount(): Int {
        return listData.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

    }

    class MultiTypeEntity<T> {

    }

}