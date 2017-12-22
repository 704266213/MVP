package com.mvp.view.handler

import com.mvp.view.IRefreshAndLoadMoreView


/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 14:22
 * 修改人：admin
 * 修改时间：2017/12/19 14:22
 * 修改备注：
 * @version
 */
class RefreshAndLoadMoreHandler<out E, in T : List<E>>(private val refreshAndLoadMoreView: IRefreshAndLoadMoreView<E, T>) : LoadMoreHandler<E, T>(refreshAndLoadMoreView) {

    override fun onSuccessHandler(entity: T?) {
        when {
            refreshAndLoadMoreView.isRefreshing() -> {
                if (entity != null) {
                    refreshAndLoadMoreView.onRefreshSuccess(entity)
                }
            }
            else -> {
                super.onSuccessHandler(entity)
            }
        }
    }

    override fun onFailureHandler(errorInfo: String) {
        when {
            refreshAndLoadMoreView.isRefreshing() -> {
                refreshAndLoadMoreView.onRefreshViewFail(errorInfo)
            }
            else -> {
                super.onFailureHandler(errorInfo)
            }
        }

    }

}