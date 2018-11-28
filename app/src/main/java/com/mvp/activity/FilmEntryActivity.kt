package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kycc.app.adapter.listener.OnItemViewClickListener
import com.mvp.R
import com.mvp.adapter.FilmAdapter
import com.mvp.divider.HorizontalDividerItemDecoration
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.FirstLoadingViewFinish
import com.mvp.http.loading.LoadingView
import com.mvp.http.loading.OnFirstLoadFinishListener
import com.mvp.presenter.GetFilmListInfoPresenter
import com.mvp.model.BannerModel
import com.mvp.model.FilmInfoModel
import com.mvp.view.FilmListInfoView
import com.mvp.widget.LoadingFooterLayout
import com.mvp.widget.loadmore.LinearLoadMoreListener
import com.mvp.widget.reflesh.OnRefreshBeginListener
import com.mvp.widget.reflesh.PtrDefaultHandler
import kotlinx.android.synthetic.main.activity_film_entry.*

class FilmEntryActivity : AppCompatActivity(), FilmListInfoView<FilmInfoModel, List<FilmInfoModel>>, OnFirstLoadFinishListener, OnRefreshBeginListener, OnStartRequestListener, OnItemViewClickListener<FilmInfoModel> {


    private var pageNo = 1
    private lateinit var getFilmListInfoRequest: GetFilmListInfoPresenter
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
        loadingView = FirstLoadingViewFinish(loadingViewLayout, this)

        refreshView.setPtrHandler(PtrDefaultHandler(this))

        /*
         * 滚动到底部自动加载更多
         */
        loadingFooterLayout = LoadingFooterLayout(this)
        loadingFooterLayout.onStartRequestListener = this
        linearLoadMoreListener = LinearLoadMoreListener(loadingFooterLayout, this)
        recyclerView.addOnScrollListener(linearLoadMoreListener)

        val itemDecoration = HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.color_ececee).sizeResId(R.dimen.dimen_1).build()
        recyclerView?.addItemDecoration(itemDecoration)

        recyclerView.layoutManager = LinearLayoutManager(this)
        filmAdapter = FilmAdapter(this)
        filmAdapter.addFooterViews(loadingFooterLayout)
        recyclerView.adapter = filmAdapter

        getFilmListInfoRequest = GetFilmListInfoPresenter(this)
        startRequest()
    }

    override fun onItemViewClickListener(clickView: View, position: Int, entity: FilmInfoModel) {
        Toast.makeText(this, "当前序号：$position", Toast.LENGTH_SHORT).show()
        when (clickView.id) {
            R.id.buyTickets -> {
                Log.e("XLog", "=========点击购票按钮 id:${clickView.id}==============")
            }
            R.id.itemView -> {
                Log.e("XLog", "=========点击itemView按钮  id:${clickView.id}==============")
            }
        }
    }

    override fun onRefreshBeginListener() {
        getFilmListInfoRequest.getFilmListInfoRequest(loadingView, "filmlist1.txt")
    }

    override fun startRequest() {
        getFilmListInfoRequest.getFilmListInfoRequest(loadingView, "filmlist$pageNo.txt")
    }

    override fun initBanner(banners: List<BannerModel>) {

    }

    override fun emptyView() {

    }

    override fun firstLoadFinish() {
        refreshView.visibility = View.VISIBLE
        loadingView = LoadingView(loadingFooterLayout)
    }

    override fun isRefreshing(): Boolean {
        return refreshView.isRefreshing
    }

    override fun onRefreshSuccess() {
        filmAdapter.clearListData()
        pageNo = 1
    }

    override fun refreshComplete() {
        refreshView.refreshComplete()
    }

    override fun isLoadMore(): Boolean {
        return linearLoadMoreListener.isLoadingMore()
    }

    override fun onSuccess(entity: List<FilmInfoModel>?) {
        if (entity != null)
            if (allDataLoadFinish(entity))
                pageNo += 1
    }

    override fun onFailure(errorInfo: String?) {
        linearLoadMoreListener.setLoadingMore(false)
    }

    private fun allDataLoadFinish(entity: List<FilmInfoModel>): Boolean {
        val size = entity.size
        val hasMore = size >= 10
        linearLoadMoreListener.setHasMore(hasMore)
        filmAdapter.isShowLoadMoreFootView = hasMore
        linearLoadMoreListener.setLoadingMore(false)
        filmAdapter.addDataToList(entity)
        return hasMore
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingView?.onDestroy()
        getFilmListInfoRequest.onDestroy()
    }

}
