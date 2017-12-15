package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.LoadingView
import com.mvp.http.request.GetFilmListInfoRequest
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.model.FilmInfoModel
import com.mvp.widget.reflesh.PtrDefaultHandler
import com.mvp.widget.reflesh.PtrFrameLayout
import com.mvp.widget.reflesh.PtrHandler
import kotlinx.android.synthetic.main.activity_film_entry.*

class FilmEntryActivity : AppCompatActivity(), OnStartRequestListener, OnResponseListener<List<FilmInfoModel>> {

    private val getFilmListInfoRequest = GetFilmListInfoRequest(this)
    private var loadingView: LoadingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_entry)

        initView()
    }

    private fun initView() {
        loadingViewLayout.onStartRequestListener = this
        loadingView = LoadingView(loadingViewLayout)
        refreshView.setPtrHandler(object : PtrHandler {
            override fun onRefreshBegin(frame: PtrFrameLayout?) = startRequest()

            override fun checkCanDoRefresh(frame: PtrFrameLayout?, content: View?, header: View?): Boolean =
                    PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)
        })
        startRequest()
      //  refreshView.isRefreshing
    }

    override fun startRequest() {
        getFilmListInfoRequest.getFilmListInfoRequest(loadingView, "filmlist1.txt")
    }

    override fun onSuccess(entity: List<FilmInfoModel>?) {
        refreshView.refreshComplete()
        Log.e("XLog", "======onSuccess======" + entity.toString())
        refreshView.visibility = View.VISIBLE
    }

    override fun onFailure(errorInfo: String) {
        refreshView.refreshComplete()
        Log.e("XLog", "======errorInfo======" + errorInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingView?.onDestroy()
        getFilmListInfoRequest.onDestroy()
    }

}
