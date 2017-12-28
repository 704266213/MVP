package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.view.base.BaseEntityView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/22 10:48
 * 修改人：admin
 * 修改时间：2017/12/22 10:48
 * 修改备注：
 * @version
 */
open class BaseEntityResponse<in T>(private var baseEntityView: BaseEntityView<T>?) : OnResponseListener<T> {


    override fun onSuccess(entity: T?) {
        baseEntityView?.onSuccess(entity)
    }

    override fun onFailure(errorInfo: String?) {
        baseEntityView?.onFailure(errorInfo)
    }

    override fun onDestroy() {
        baseEntityView = null
    }

}