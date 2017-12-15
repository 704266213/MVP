package com.happy.food.interceptor

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 服务器不支持缓存，本地使用缓存
 */
class NativeCacheInterceptor(private val effectiveTime: Int) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //服务器不支持缓存，本地使用缓存，执行拦截器
        val request = chain.request()
        val response = chain.proceed(request)
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for day
                // smax-age 文件的有效时间
                //max-stale 文件的存储时间
                .header("Cache-Control", "max-age=" + effectiveTime + " ,max-stale=" + Integer.MIN_VALUE)
                .build()
    }
}
