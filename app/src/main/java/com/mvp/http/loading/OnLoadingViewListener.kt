package com.mvp.http.loading

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 10:01
 * 修改人：admin
 * 修改时间：2017/12/13 10:01
 * 修改备注：
 * @version
 */
interface OnLoadingViewListener {

    fun showLoadingView()

    fun showSuccessView()

    fun showErrorView()
}