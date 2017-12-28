package com.mvp.http.response

import com.mvp.view.base.LoadMoreView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/22 10:37
 * 修改人：admin
 * 修改时间：2017/12/22 10:37
 * 修改备注：
 * @version
 */
open class LoadMoreResponse<out E, in T : List<E>>(private var loadMoreView: LoadMoreView<E, T>?) : BaseEntryResponse<E, T>(loadMoreView) {


    override fun onSuccess(entity: T?) {
        var isLoadMore = loadMoreView?.isLoadMore() ?: false
        if (isLoadMore) {
            loadMoreView?.onSuccess(entity)
        } else {
            super.onSuccess(entity)
        }
    }


    override fun onFailure(errorInfo: String?) {
        var isLoadMore = loadMoreView?.isLoadMore() ?: false
        if (isLoadMore) {
            loadMoreView?.onFailure(errorInfo)
        } else {
            super.onFailure(errorInfo)
        }
    }

    override fun onDestroy() {
        loadMoreView = null
    }

}