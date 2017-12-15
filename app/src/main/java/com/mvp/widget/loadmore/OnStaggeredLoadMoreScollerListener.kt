package com.mvp.widget.loadmore

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.OnLoadingViewListener


class OnStaggeredLoadMoreScollerListener(onLoadingViewListener: OnLoadingViewListener, onStartRequestListener: OnStartRequestListener) : OnLoadMoreScrollListener(onLoadingViewListener,onStartRequestListener) {

    /**
     * 最后一个的位置
     */
    private var lastPositions: IntArray? = null

    override fun getLastVisibleItemPosition(recyclerView: RecyclerView?): Int {
        val layoutManager = recyclerView!!.layoutManager
        val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
        if (lastPositions != null) {
            lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
        }
        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
        return findMax(lastPositions)
    }

    private fun findMax(lastPositions: IntArray?): Int {
        var max = lastPositions!![0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

}
