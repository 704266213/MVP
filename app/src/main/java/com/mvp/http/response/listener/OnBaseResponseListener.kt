package com.mvp.http.response.listener

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2018/7/23 14:20
 * 修改人：admin
 * 修改时间：2018/7/23 14:20
 * 修改备注：
 * @version
 */
interface OnBaseResponseListener {

    fun <T> onSuccess(entity: T?)

    fun onFailure(errorInfo: String?)

    fun onDestroy()
}