package com.kycc.app.adapter.listener

import android.view.View

/**
 * 项目名称：HappyFood
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/8/29 11:53
 * 修改人：admin
 * 修改时间：2017/8/29 11:53
 * 修改备注：
 * @version
 */
class OnRecyclerViewClickListener<T>(private val onItemViewClickListener: OnItemViewClickListener<T>, private val position: Int, private val entity: T)
    : View.OnClickListener {

    override fun onClick(clickView: View) =
            onItemViewClickListener.onItemViewClickListener(clickView, position, entity)
}