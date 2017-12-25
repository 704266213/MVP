package com.mvp.view.base


/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 14:14
 * 修改人：admin
 * 修改时间：2017/12/19 14:14
 * 修改备注：
 * @version
 */
interface IRefreshAndLoadMoreView<out E, in T : List<E>> : IEntryRefreshView<T>, ILoadMoreAndInitView<E, T> {


}