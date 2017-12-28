package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.mvp.adapter.LoadMoreRecycleAdapter
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.view.base.BaseEntryView
import com.mvp.view.base.LoadMoreView
import com.mvp.widget.LoadingFooterLayout
import com.mvp.widget.loadmore.LinearLoadMoreListener

abstract class LoadMoreActivity<T, in L : List<T>> : AppCompatActivity(), OnStartRequestListener, LoadMoreView<T, L> {

    protected var pageNo = 1
    protected lateinit var loadingFooterLayout: LoadingFooterLayout
    private lateinit var linearLoadMoreListener: LinearLoadMoreListener
    private lateinit var loadMoreAdapter: LoadMoreRecycleAdapter<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    internal fun initLoadMore(recyclerView: RecyclerView) {
        /*
       * 滚动到底部自动加载更多
       */
        loadingFooterLayout = LoadingFooterLayout(this)
        loadingFooterLayout.onStartRequestListener = this
        linearLoadMoreListener = LinearLoadMoreListener(loadingFooterLayout, this)
        recyclerView.addOnScrollListener(linearLoadMoreListener)

        createItemDecoration()

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadMoreAdapter = getLoadMoreAdapter()
        loadMoreAdapter.addFooterViews(loadingFooterLayout)
        recyclerView.adapter = loadMoreAdapter
    }

    abstract fun createItemDecoration()

    abstract fun getLoadMoreAdapter(): LoadMoreRecycleAdapter<T>


    override fun isLoadMore(): Boolean {
        return linearLoadMoreListener.isLoadingMore()
    }

    override fun onSuccess(entity: L?) {
        if (entity != null)
            if (allDataLoadFinish(entity))
                pageNo += 1
    }

    override fun onFailure(errorInfo: String?) {
        linearLoadMoreListener.setLoadingMore(false)
    }

    private fun allDataLoadFinish(entity: List<T>): Boolean {
        val size = entity.size
        val hasMore = size >= 10
        linearLoadMoreListener.setHasMore(hasMore)
        loadMoreAdapter.isShowLoadMoreFootView = hasMore
        linearLoadMoreListener.setLoadingMore(false)
        loadMoreAdapter.addDataToList(entity)
        return hasMore
    }

}
