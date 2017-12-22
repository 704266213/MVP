package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.view.handler.EntryHandler


/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/20 11:35
 * 修改人：admin
 * 修改时间：2017/12/20 11:35
 * 修改备注：
 * @version
 */
class EntryResponse<out E, in T : List<E>>(private var entryHandler: EntryHandler<E, T>) : OnResponseListener<T> {


    override fun onSuccess(entity: T?) {
        entryHandler.onSuccessHandler(entity)
    }

    override fun onFailure(errorInfo: String) {
        entryHandler.onFailureHandler(errorInfo)
    }

}