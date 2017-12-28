/**
 *
 */
package com.mvp.widget


import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.OnLoadingViewListener
import kotlinx.android.synthetic.main.loading_error_layout.view.*
import kotlinx.android.synthetic.main.loading_layout.view.*

class LoadingViewLayout : ConstraintLayout, View.OnClickListener, OnLoadingViewListener {

    private var loadingErrorView: View? = null
    var onStartRequestListener: OnStartRequestListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    private fun initView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.loading_layout, this)
    }

    override fun onClick(v: View) {
        if (onStartRequestListener != null) {
            showLoadingView()
            onStartRequestListener?.startRequest()
        }
    }

    override fun showLoadingView() {
        progressBar!!.visibility = View.VISIBLE
        loadingTip.visibility = View.VISIBLE
        loadingErrorView?.visibility = View.GONE
    }

    override fun showSuccessView() {
        visibility = View.GONE

        val parentView = parent as ViewGroup
        parentView.removeView(this)
    }

    override fun showErrorView() {
        progressBar!!.visibility = View.GONE
        loadingTip.visibility = View.GONE
        if (loadingErrorView == null) {
            loadingErrorView = viewStub.inflate()
            refresh.setOnClickListener(this)
        } else {
            loadingErrorView?.visibility = View.VISIBLE
        }
    }

}
