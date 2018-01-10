package com.mvp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.OnLoadingViewListener
import kotlinx.android.synthetic.main.footer_loading.view.*


class LoadingFooterLayout : LinearLayout, OnLoadingViewListener {

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

        val layoutFooterParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, resources.getDimension(R.dimen.dimen_60).toInt())
        layoutParams = layoutFooterParams
        this.gravity = Gravity.CENTER

        loadMore.setOnClickListener {
            showLoadingView()
            if (onStartRequestListener != null) {
                onStartRequestListener?.startRequest()
            }
        }
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
