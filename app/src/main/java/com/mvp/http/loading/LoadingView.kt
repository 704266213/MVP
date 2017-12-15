package com.mvp.http.loading

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/14 11:35
 * 修改人：admin
 * 修改时间：2017/12/14 11:35
 * 修改备注：
 * @version
 */

/*
 * kotlin 的代理对象
 */
//class LoadingView(private var onLoadingViewListener: OnLoadingViewListener?) : OnLoadingViewListener by onLoadingViewListener
class LoadingView(private var onLoadingViewListener: OnLoadingViewListener?) : OnLoadingViewListener {

    override fun showSuccessView() {
        onLoadingViewListener?.showSuccessView()
    }

    override fun showErrorView() {
        onLoadingViewListener?.showErrorView()
    }

    override fun showLoadingView() {
        onLoadingViewListener?.showLoadingView()
    }

    fun onDestroy() {
        onLoadingViewListener = null
    }

}