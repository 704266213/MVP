package com.mvp.widget.loadmore

import android.support.v7.widget.RecyclerView
import com.mvp.http.listener.OnStartRequestListener

import com.mvp.http.loading.OnLoadingViewListener

abstract class OnLoadMoreScrollListener(private val onLoadingViewListener: OnLoadingViewListener, private val onStartRequestListener: OnStartRequestListener) : RecyclerView.OnScrollListener() {

    /**
     * 最后一个可见的item的位置
     */
    private var lastVisibleItemPosition: Int = 0
    /**
     * 是否正在加载
     */

    //是否正在加载数据
    private var isLoadingMore = false
    //是否有更多数据
    private var hasMore = true

    /**
     * 当前滑动的状态
     */
    private var currentScrollState: Int = 0


    private var onScrollListener: OnScrollListener? = null

    fun isLoadingMore(): Boolean {
        return isLoadingMore
    }

    fun setLoadingMore(loadingMore: Boolean) {
        isLoadingMore = loadingMore
    }

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (onScrollListener != null) {
            onScrollListener!!.onScrolled(recyclerView, dx, dy)
        }
        if (hasMore) {
            lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (onScrollListener != null) {
            onScrollListener!!.onScrollStateChanged(recyclerView, newState)
        }
        if (hasMore) {
            currentScrollState = newState
            val layoutManager = recyclerView!!.layoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1) {
                //自动显示加载更多布局的加载中的布局
                if (onLoadingViewListener != null && hasMore) {
                    onLoadingViewListener!!.showLoadingView()
                }
                if (onStartRequestListener != null && !isLoadingMore) {
                    isLoadingMore = true
                    onStartRequestListener!!.startRequest()
                }
            }
        }
    }


    abstract fun getLastVisibleItemPosition(recyclerView: RecyclerView?): Int

    fun addOnScrollListener(onScrollListener: OnScrollListener) {
        this.onScrollListener = onScrollListener
    }


    interface OnScrollListener {

        fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int)

        fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)

    }

}
