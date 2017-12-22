package com.mvp.view.handler

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 14:34
 * 修改人：admin
 * 修改时间：2017/12/19 14:34
 * 修改备注：
 * @version
 */
interface IResponseHandler<in T> {

    fun onSuccessHandler(entity: T?)

    fun onFailureHandler(errorInfo: String)
}