package com.mvp.view.handler

import com.mvp.view.ILoadMoreAndInitView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 15:52
 * 修改人：admin
 * 修改时间：2017/12/19 15:52
 * 修改备注：
 * @version
 */
open class LoadMoreHandler<out E, in T : List<E>>(private val loadMoreAndInitView: ILoadMoreAndInitView<E, T>) : IRefreshAndLoadMoreHandler<T> {


    override fun onSuccessHandler(entity: T) {
        when {
            loadMoreAndInitView.isInitView() -> {
                loadMoreAndInitView.initViewSuccess(entity)
                if (entity.isEmpty()) {
                    loadMoreAndInitView.emptyView()
                }
            }
            loadMoreAndInitView.isLoadMore() -> {
                loadMoreAndInitView.loadMoreSuccess(entity)
            }
        }
    }

    override fun onFailureHandler(errorInfo: String) {
        when {
            loadMoreAndInitView.isInitView() -> {
                loadMoreAndInitView.initViewFail(errorInfo)
            }
            loadMoreAndInitView.isLoadMore() -> {
                loadMoreAndInitView.loadMoreFailure(errorInfo)
            }
        }

    }


}