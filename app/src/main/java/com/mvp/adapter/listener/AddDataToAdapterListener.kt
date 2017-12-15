package com.mvp.adapter.listener

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 17:10
 * 修改人：admin
 * 修改时间：2017/12/13 17:10
 * 修改备注：
 * @version
 */
interface AddDataToAdapterListener<in T> {

    fun addDataToList(listData: List<T>)
}