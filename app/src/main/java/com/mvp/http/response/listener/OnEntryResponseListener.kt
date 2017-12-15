package com.mvp.http.response.listener

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 17:59
 * 修改人：admin
 * 修改时间：2017/12/13 17:59
 * 修改备注：
 * @version
 */
interface OnEntryResponseListener<T, in E : List<T>> {

    fun onSuccess(entity: E?)

    fun onFailure(errorInfo: String)

}