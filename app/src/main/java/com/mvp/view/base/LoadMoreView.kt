package com.mvp.view.base

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 9:59
 * 修改人：admin
 * 修改时间：2017/12/19 9:59
 * 修改备注：
 * @version
 */
interface LoadMoreView<out E, in T : List<E>> : BaseEntryView<E,T>{

    //是否正在加载更多
    fun isLoadMore(): Boolean

}