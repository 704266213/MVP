package com.happy.food.manager

import com.happy.food.interceptor.NativeCacheInterceptor
import com.happy.food.interceptor.NetworkCacheInterceptor
import com.mvp.MVPApplication
import com.mvp.http.retrofit.BaseRetrofit
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 项目名称：HappyFood
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/8/28 9:52
 * 修改人：admin
 * 修改时间：2017/8/28 9:52
 * 修改备注：
 * @version
 */
class CacheRetrofit : BaseRetrofit() {

    //缓存的大小
    private val cacheSize: Long = 10 * 1024 * 1024
    //请求超时时间
    private val timeOut: Long = 30

    private var cache: Cache

    init {
        val applicationContext = MVPApplication.getMVPContext()
        val cacheFile = File(applicationContext?.cacheDir, "mvpCache")
        cache = Cache(cacheFile, cacheSize)
    }

    override fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(NativeCacheInterceptor(30))
            .addNetworkInterceptor(NetworkCacheInterceptor())
            .retryOnConnectionFailure(true)
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .cache(cache)
            .build()
}