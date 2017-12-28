package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.view.base.BaseEntryView


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
open class BaseEntryResponse<out E, in T : List<E>>(private var baseEntityView: BaseEntryView<E, T>?) : OnResponseListener<T> {

    override fun onSuccess(entity: T?) {
        if (entity != null && !entity.isEmpty()) {
            baseEntityView?.onSuccess(entity)
        } else {
            baseEntityView?.emptyView()
        }
    }

    override fun onFailure(errorInfo: String?) {
        baseEntityView?.onFailure(errorInfo)
    }

    override fun onDestroy() {
        baseEntityView = null
    }

}