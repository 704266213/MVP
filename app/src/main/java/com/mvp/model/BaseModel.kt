package com.mvp.model

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 9:36
 * 修改人：admin
 * 修改时间：2017/12/13 9:36
 * 修改备注：
 * @version
 */
open class BaseModel<T> {

    constructor()

    var state = -1
    var message = ""
    var result: T? = null

}