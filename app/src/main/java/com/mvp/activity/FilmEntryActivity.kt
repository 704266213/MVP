package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mvp.R
import com.mvp.adapter.FilmAdapter
import com.mvp.divider.HorizontalDividerItemDecoration
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.LoadingView
import com.mvp.http.request.GetFilmListInfoRequest
import com.mvp.model.BannerModel
import com.mvp.model.FilmInfoModel
import com.mvp.view.FilmListInfoView
import com.mvp.widget.LoadingFooterLayout
import com.mvp.widget.loadmore.LinearLoadMoreListener
import com.mvp.widget.reflesh.PtrDefaultHandler
import com.mvp.widget.reflesh.PtrFrameLayout
import com.mvp.widget.reflesh.PtrHandler
import kotlinx.android.synthetic.main.activity_film_entry.*

class FilmEntryActivity : AppCompatActivity(), OnStartRequestListener, FilmListInfoView {

    private var pageNo = 1
    private var isInitView = true
    private lateinit var getFilmListInfoRequest: GetFilmListInfoRequest
    private var loadingView: LoadingView? = null
    private lateinit var filmAdapter: FilmAdapter
    private lateinit var loadingFooterLayout: LoadingFooterLayout
    private lateinit var linearLoadMoreListener: LinearLoadMoreListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_entry)

        initView()
    }

    private fun initView() {
        loadingViewLayout.onStartRequestListener = this
        loadingView = LoadingView(loadingViewLayout)
        refreshView.setPtrHandler(object : PtrHandler {
            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                pageNo = 1
                startRequest()
            }

            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View?, header: View?): Boolean =
                    PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
        })

        loadingFooterLayout = LoadingFooterLayout(this)
        loadingFooterLayout.onStartRequestListener = this
        val itemDecoration = HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.color_ececee).sizeResId(R.dimen.dimen_1).build()
        recyclerView?.addItemDecoration(itemDecoration)

        recyclerView.layoutManager = LinearLayoutManager(this)
        filmAdapter = FilmAdapter(this)
        filmAdapter.addFooterViews(loadingFooterLayout)
        recyclerView.adapter = filmAdapter

        /*
         * 滚动到底部自动加载更多
         */
        linearLoadMoreListener = LinearLoadMoreListener(loadingFooterLayout, this)
        recyclerView.addOnScrollListener(linearLoadMoreListener)
        getFilmListInfoRequest = GetFilmListInfoRequest(this)
        startRequest()
    }

    override fun startRequest() {
        getFilmListInfoRequest.getFilmListInfoRequest(loadingView, "filmlist$pageNo.txt")
    }


    override fun initBanner(banners: List<BannerModel>) {

    }

    override fun isInitView(): Boolean {
        return isInitView
    }

    override fun emptyView() {

    }

    override fun initViewSuccess(entity: List<FilmInfoModel>) {
        refreshView.visibility = View.VISIBLE
        isInitView = false
        loadingView = LoadingView(loadingFooterLayout)
        allDataLoadFinish(entity)
        pageNo += 1
    }

    override fun initViewFail(errorInfo: String) {

    }

    override fun isRefreshing(): Boolean {
        return refreshView.isRefreshing
    }

    override fun onRefreshSuccess(entity: List<FilmInfoModel>) {
        filmAdapter.clearListData()
        allDataLoadFinish(entity)
        refreshView.refreshComplete()
        pageNo = 2
    }

    override fun onRefreshViewFail(errorInfo: String) {
        refreshView.refreshComplete()
        Toast.makeText(this, "下拉刷新失败", Toast.LENGTH_SHORT).show()
    }

    override fun isLoadMore(): Boolean {
        return linearLoadMoreListener.isLoadingMore()
    }

    override fun loadMoreSuccess(entity: List<FilmInfoModel>) {
        allDataLoadFinish(entity)
        pageNo += 1
    }

    override fun loadMoreFailure(errorInfo: String) {
        linearLoadMoreListener.setLoadingMore(false)
    }

    private fun allDataLoadFinish(entity: List<FilmInfoModel>) {
        val size = entity.size
        val hasMore = size >= 10
        linearLoadMoreListener.setHasMore(hasMore)
        filmAdapter.isShowLoadMoreFootView = hasMore
        linearLoadMoreListener.setLoadingMore(false)
        filmAdapter.addDataToList(entity)
    }


    override fun onDestroy() {
        super.onDestroy()
        loadingView?.onDestroy()
        getFilmListInfoRequest.onDestroy()
    }

}
