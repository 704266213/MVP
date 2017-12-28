package com.mvp.http.response

import com.mvp.view.base.RefreshView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/28 15:53
 * 修改人：admin
 * 修改时间：2017/12/28 15:53
 * 修改备注：
 * @version
 */
class RefreshEntityResponse<in T>(private var refreshView: RefreshView<T>?) : BaseEntityResponse<T>(refreshView) {

    override fun onSuccess(entity: T?) {
        super.onSuccess(entity)
        refreshView?.refreshComplete()
    }

    override fun onFailure(errorInfo: String?) {
        super.onFailure(errorInfo)
        refreshView?.refreshComplete()
    }

    override fun onDestroy() {
        refreshView = null
    }
}