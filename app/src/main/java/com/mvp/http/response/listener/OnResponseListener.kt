package com.mvp.http.response.listener

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 9:47
 * 修改人：admin
 * 修改时间：2017/12/13 9:47
 * 修改备注：
 * @version
 */
interface OnResponseListener<in T> {

    fun onSuccess(entity: T?)

    fun onFailure(errorInfo: String?)

    fun onDestroy()
}