package com.mvp.view.base

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 9:58
 * 修改人：admin
 * 修改时间：2017/12/19 9:58
 * 修改备注：
 * @version
 */
interface RefreshView<in T> : BaseEntityView<T> {

    fun refreshComplete()

}