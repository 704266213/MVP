package com.mvp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout

import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.OnLoadingViewListener
import kotlinx.android.synthetic.main.footer_loading.view.*


class LoadingFooterLayout : RelativeLayout, OnLoadingViewListener {

    var onStartRequestListener: OnStartRequestListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.footer_loading, this)

        loadMore.setOnClickListener {
            showLoadingView()
            if (onStartRequestListener != null) {
                onStartRequestListener?.startRequest()
            }
        }
    }


    fun showNoMoreView() {
        progressBar.visibility = View.GONE
        loadMore.visibility = View.VISIBLE
        loadMore.isClickable = false
        loadMore.text = resources.getString(R.string.no_more_load)
    }

    override fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
        loadMore.visibility = View.GONE
    }

    override fun showSuccessView() {

    }

    override fun showErrorView() {
        progressBar.visibility = View.GONE
        loadMore.visibility = View.VISIBLE
        loadMore.isClickable = true
        loadMore.text = resources.getString(R.string.loading_more)
    }
}
