package com.mvp.view.base

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 9:56
 * 修改人：admin
 * 修改时间：2017/12/19 9:56
 * 修改备注：
 * @version
 */
interface BaseEntityView<in T> {

    fun onSuccess(entity: T?)

    fun onFailure(errorInfo: String?)

}