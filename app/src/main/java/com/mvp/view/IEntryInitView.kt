package com.mvp.view

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 10:20
 * 修改人：admin
 * 修改时间：2017/12/19 10:20
 * 修改备注：
 * @version
 */
interface IEntryInitView<out E, in T : List<E>> : InitView<T> {

    fun emptyView()

}