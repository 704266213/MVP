package com.mvp.widget.loadmore

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.OnLoadingViewListener


class OnGridMoreScorllListener(onLoadingViewListener: OnLoadingViewListener, onStartRequestListener: OnStartRequestListener) : OnLoadMoreScrollListener(onLoadingViewListener,onStartRequestListener) {

    override fun getLastVisibleItemPosition(recyclerView: RecyclerView?): Int {
        val layoutManager = recyclerView!!.layoutManager
        var lastVisibleItemPosition = -1
        if (layoutManager != null) {
            lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }
        return lastVisibleItemPosition
    }

}
