package com.mvp

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 10:36
 * 修改人：admin
 * 修改时间：2017/12/13 10:36
 * 修改备注：
 * @version
 */
class MVPApplication : Application() {

    companion object {
        @JvmField
        var context: Context? = null

        @JvmStatic
        fun getMVPContext(): Context = context!!

    }


    override fun onCreate() {
        super.onCreate()

        MVPApplication.context = applicationContext
        //内存泄漏检测
        LeakCanary.install(this)
    }
}