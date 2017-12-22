package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.view.handler.RefreshAndLoadMoreHandler

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/22 10:41
 * 修改人：admin
 * 修改时间：2017/12/22 10:41
 * 修改备注：
 * @version
 */
class RefreshAndLoadMoreResponse<out E, in T : List<E>>(private var refreshAndLoadMoreHandler: RefreshAndLoadMoreHandler<E, T>) : OnResponseListener<T> {


    override fun onSuccess(entity: T?) {
        refreshAndLoadMoreHandler.onSuccessHandler(entity)
    }

    override fun onFailure(errorInfo: String) {
        refreshAndLoadMoreHandler.onFailureHandler(errorInfo)
    }

}