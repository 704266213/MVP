package com.mvp.http.response

import com.mvp.view.base.RefreshLoadMoreView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/28 16:03
 * 修改人：admin
 * 修改时间：2017/12/28 16:03
 * 修改备注：
 * @version
 */
class RefreshLoadMoreResponse<out E, in T : List<E>>(private var refreshLoadMoreView: RefreshLoadMoreView<E, T>?) : LoadMoreResponse<E, T>(refreshLoadMoreView) {

    override fun onSuccess(entity: T?) {
        var isRefreshing = refreshLoadMoreView?.isRefreshing() ?: false
        if (isRefreshing) {
            refreshLoadMoreView?.refreshComplete()
            refreshLoadMoreView?.onRefreshSuccess()
            refreshLoadMoreView?.onSuccess(entity)
        } else {
            super.onSuccess(entity)
        }
    }

    override fun onFailure(errorInfo: String?) {
        var isRefreshing = refreshLoadMoreView?.isRefreshing() ?: false
        if (isRefreshing) {
            refreshLoadMoreView?.refreshComplete()
        } else {
            super.onFailure(errorInfo)
        }
    }

    override fun onDestroy() {
        refreshLoadMoreView = null
    }
}