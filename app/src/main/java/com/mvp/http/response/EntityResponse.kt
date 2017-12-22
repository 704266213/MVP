package com.mvp.http.response

import com.mvp.http.response.listener.OnResponseListener
import com.mvp.model.BaseModel
import com.mvp.view.InitView

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
class EntityResponse<T, in M : BaseModel<T>>(private var initView: InitView<T>?) : OnResponseListener<T> {

    override fun onSuccess(entity: T?) {
        initView?.initViewSuccess(entity)
    }

    override fun onFailure(errorInfo: String) {
        initView?.initViewFail(errorInfo)
    }


}