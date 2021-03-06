package com.mvp.activity

import android.os.Bundle
import android.view.View
import com.mvp.R
import com.mvp.adapter.CinemaAdapter
import com.mvp.adapter.LoadMoreRecycleAdapter
import com.mvp.divider.HorizontalDividerItemDecoration
import com.mvp.http.loading.FirstLoadingViewFinish
import com.mvp.http.loading.LoadingView
import com.mvp.http.loading.OnFirstLoadFinishListener
import com.mvp.presenter.CinemaPresenter
import com.mvp.model.CinemaModel
import kotlinx.android.synthetic.main.activity_film_entry.*

class CinemaActivity : LoadMoreActivity<CinemaModel, List<CinemaModel>>(), OnFirstLoadFinishListener {

    private var loadingView: LoadingView? = null
    private val cinemaRequest = CinemaPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cinema)

        intView()
    }

    private fun intView() {
        loadingViewLayout.onStartRequestListener = this
        loadingView = FirstLoadingViewFinish(loadingViewLayout, this)
        initLoadMore(recyclerView)
        startRequest()
    }

    override fun createItemDecoration() {
        val itemDecoration = HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.color_ececee).sizeResId(R.dimen.dimen_1).build()
        recyclerView?.addItemDecoration(itemDecoration)
    }

    override fun getLoadMoreAdapter(): LoadMoreRecycleAdapter<CinemaModel> {
        return CinemaAdapter()
    }

    override fun startRequest() {
        cinemaRequest.getUserInfoRequest(loadingView, "cinemalist$pageNo.txt")
    }

    override fun emptyView() {

    }

    override fun firstLoadFinish() {
        recyclerView.visibility = View.VISIBLE
        loadingView = LoadingView(loadingFooterLayout)
    }


    override fun onDestroy() {
        super.onDestroy()
        cinemaRequest.onDestroy()
        loadingView?.onDestroy()
    }


}
