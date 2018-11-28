package com.mvp.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.mvp.adapter.listener.IBinderViewHolder

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2018/11/27 16:48
 * 修改人：admin
 * 修改时间：2018/11/27 16:48
 * 修改备注：
 * @version
 */

class RecyclerAdapter(private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val firstIndexForAdapter = 100000
    private val headViewBinder = mutableListOf<IBinderViewHolder<RecyclerView.ViewHolder>>()
    private val footViewBinder = mutableListOf<IBinderViewHolder<RecyclerView.ViewHolder>>()

    override fun getItemCount(): Int {
        return headViewBinder.size + footViewBinder.size + adapter.itemCount
    }

    /*
     * 当position为头部或者尾部的时候返回 当前序号
     *
     */
    override fun getItemViewType(position: Int): Int {
        val headSize = headViewBinder.size
        val adapterItemCount = adapter.itemCount
        val footTypeIndex = headSize + adapterItemCount
        return when (position) {
            in 0..headSize -> position
            in footTypeIndex..itemCount -> position - adapterItemCount
            else -> adapter.getItemViewType(position - headSize) + firstIndexForAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val headSize = headViewBinder.size
        val footSize = footViewBinder.size
        val maxFootIndex = headSize + footSize
        val footIndex = viewType - headSize
        return when (viewType) {
            in 0..headSize -> headViewBinder[viewType].onCreateViewHolder(parent, viewType)
            in headSize..maxFootIndex -> footViewBinder[footIndex].onCreateViewHolder(parent, viewType)
            else -> adapter.onCreateViewHolder(parent, viewType - firstIndexForAdapter)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val headSize = headViewBinder.size
        val adapterItemCount = adapter.itemCount
        val minFootIndex = headSize + adapterItemCount
        return when (position) {
            in 0..headSize -> headViewBinder[position].onBindViewHolder(holder!!, position)
            in minFootIndex..itemCount -> footViewBinder[position - adapter.itemCount - headViewBinder.size].onBindViewHolder(holder, position)
            else -> adapter.onBindViewHolder(holder, position)
        }
    }

    fun addHeadBinder(binderViewHolder: IBinderViewHolder<RecyclerView.ViewHolder>) {
        headViewBinder.add(binderViewHolder)
    }

    fun removeHeadBinder(binderViewHolder: IBinderViewHolder<RecyclerView.ViewHolder>) {
        headViewBinder.remove(binderViewHolder)
    }

    fun addFootBinder(binderViewHolder: IBinderViewHolder<RecyclerView.ViewHolder>) {
        footViewBinder.add(binderViewHolder)
    }

    fun removeFootBinder(binderViewHolder: IBinderViewHolder<RecyclerView.ViewHolder>) {
        footViewBinder.remove(binderViewHolder)
    }

}
