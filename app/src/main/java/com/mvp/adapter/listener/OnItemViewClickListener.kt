package com.kycc.app.adapter.listener

import android.view.View

/**
 * 项目名称：HappyFood
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/8/29 11:48
 * 修改人：admin
 * 修改时间：2017/8/29 11:48
 * 修改备注：
 * @version
 */
interface OnItemViewClickListener<in T> {

    fun onItemViewClickListener(clickView : View, position : Int , entity : T)
}