package com.mvp.view

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
interface ILoadMoreView<in T> {

    //是否正在加载更多
    fun isLoadMore(): Boolean

    //数据已经加载完成
    fun loadMoreSuccess(entity: T)

    //数据已经加载失败
    fun loadMoreFailure(errorInfo: String)

}