package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.view.handler.EntryHandler
import com.mvp.view.handler.LoadMoreHandler

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
class LoadMoreResponse<out E, in T : List<E>>(private var loadMoreHandler: LoadMoreHandler<E, T>) : OnResponseListener<T> {


    override fun onSuccess(entity: T?) {
        loadMoreHandler.onSuccessHandler(entity)
    }

    override fun onFailure(errorInfo: String) {
        loadMoreHandler.onFailureHandler(errorInfo)
    }

}