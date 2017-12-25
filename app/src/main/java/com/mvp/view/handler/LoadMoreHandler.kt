package com.mvp.view.handler

import com.mvp.view.base.ILoadMoreAndInitView

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
open class LoadMoreHandler<out E, in T : List<E>>(private val loadMoreAndInitView: ILoadMoreAndInitView<E, T>) : EntryHandler<E, T>(loadMoreAndInitView) {

    override fun onSuccessHandler(entity: T?) {
        if (loadMoreAndInitView.isLoadMore()) {
            if (entity != null) {
                loadMoreAndInitView.loadMoreSuccess(entity)
            }
        } else {
            super.onSuccessHandler(entity)
        }
    }

    override fun onFailureHandler(errorInfo: String) {
        if (loadMoreAndInitView.isLoadMore()) {
            loadMoreAndInitView.loadMoreFailure(errorInfo)
        } else {
            super.onFailureHandler(errorInfo)
        }
    }
}


