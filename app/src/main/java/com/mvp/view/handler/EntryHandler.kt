package com.mvp.view.handler

import com.mvp.view.IEntryInitView

/**
 * 项目名称：MVP
 * 类描述：初始化请求列表数据时的处理
 * 创建人：admin
 * 创建时间：2017/12/22 9:35
 * 修改人：admin
 * 修改时间：2017/12/22 9:35
 * 修改备注：
 * @version
 */
open class EntryHandler<out E, in T : List<E>>(private val entryInitView: IEntryInitView<E, T>) : IResponseHandler<T> {


    override fun onSuccessHandler(entity: T?) {
        if (entity != null && !entity.isEmpty()) {
            entryInitView.initViewSuccess(entity)
        } else {
            entryInitView.emptyView()
        }
    }

    override fun onFailureHandler(errorInfo: String) {
        entryInitView.initViewFail(errorInfo)
    }


}