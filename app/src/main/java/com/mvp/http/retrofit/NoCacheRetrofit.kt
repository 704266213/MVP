package com.happy.food.manager

import com.mvp.http.interceptor.NativeCacheInterceptor
import com.mvp.http.retrofit.BaseRetrofit
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 项目名称：HappyFood
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/8/28 14:26
 * 修改人：admin
 * 修改时间：2017/8/28 14:26
 * 修改备注：
 * @version
 */
class NoCacheRetrofit : BaseRetrofit() {

    //请求超时时间
    private val timeOut: Long = 30

    override fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(NativeCacheInterceptor(30))
            .retryOnConnectionFailure(true)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .build()


}