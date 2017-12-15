package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.View
import com.mvp.adapter.listener.AddDataToAdapterListener
import com.mvp.widget.LoadingFooterLayout

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
open abstract class LoadMoreRecycleAdapter<in T, VH : RecyclerView.ViewHolder>(private val adapter: RecyclerView.Adapter<VH>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AddDataToAdapterListener<T> {

    private val headType = 0
    private val itemType = 1
    private val foodType = 2

    private val listData = mutableListOf<T>()
    private var footerView: View? = null


    fun addFooterViews(footerView: View) {
        this.footerView = footerView
    }

    var isShowLoadMoreFootView = true

    override fun addDataToList(listData: List<T>) {
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        val size = adapter.itemCount
        return if (size == 0) 0 else size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return foodType
//        return when (position) {
//
//                foodType
//            }
//            else -> {
//                itemType
//            }
//        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateItemViewHolder(viewGroup, viewType)

//        return when (viewType) {
//            headType -> {
//                ItemViewHolder(headerViews.get(g))
//            }
//            foodType -> {
//                ItemViewHolder()
//            }
//            else -> {
//                onCreateItemViewHolder(viewGroup, viewType)
//            }
//        }
    }


    override fun onBindViewHolder(recyclerViewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == itemType) {
            onItemViewHolder(recyclerViewHolder as VH, position)
        }
    }

    abstract fun onItemViewHolder(recyclerViewHolder: VH, position: Int)


    abstract fun onCreateItemViewHolder(viewGroup: ViewGroup, viewType: Int): VH


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}