package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import com.mvp.adapter.listener.AddDataToAdapterListener
import com.mvp.model.FilmInfoModel

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 16:43
 * 修改人：admin
 * 修改时间：2017/12/13 16:43
 * 修改备注：
 * @version
 */
open abstract class LoadMoreRecycleAdapter<in T> : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AddDataToAdapterListener<T> {

    private val itemType = 1
    private val foodType = 2

    private val listData = mutableListOf<T>()
    private lateinit var footerView: View
    var isShowLoadMoreFootView = true


    fun addFooterViews(footerView: View) {
        this.footerView = footerView
    }


    override fun addDataToList(listData: List<T>) {
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    fun clearListData() {
        this.listData.clear()
    }

    fun addDataToList(listData: List<T>, isShowLoadMoreFootView: Boolean) {
        this.isShowLoadMoreFootView = isShowLoadMoreFootView
        addDataToList(listData)
    }

    override fun getItemCount(): Int {
        val size = listData.size
        return if (size == 0) 0 else if (isShowLoadMoreFootView) size + 1 else size
    }

    override fun getItemViewType(position: Int): Int {
        val size = itemCount
        return if (!isShowLoadMoreFootView) itemType else if (position == size - 1) foodType else itemType
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == foodType) {
            return FooterHolder(footerView)
        } else {
            onCreateItemViewHolder(viewGroup, viewType)
        }
    }


    override fun onBindViewHolder(recyclerViewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == itemType) {
            onBindViewHolder(recyclerViewHolder, position, listData[position])
        }
    }

    abstract fun onCreateItemViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder


    abstract fun onBindViewHolder(recyclerViewHolder: RecyclerView.ViewHolder, position: Int, entity: T)


    class FooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}