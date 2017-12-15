package com.happy.food.interceptor

import android.text.TextUtils

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response


class NetworkCacheInterceptor : Interceptor {

    // maxAge 文件的有效时间
    private val maxAge: Int = 60
    //maxStale 文件的存储时间
    private val maxStale: Int = 6000

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //服务器不支持缓存，本地使用缓存，执行拦截器
        val request = chain.request()
        val response = chain.proceed(request)

        var cacheControl = request.cacheControl().toString()
        if (TextUtils.isEmpty(cacheControl)) {
            cacheControl = "public, max-age= $maxAge ,max-stale= $maxStale"
        }
        return response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", cacheControl)
                .build()


        //        Request request = chain.request();
        //        boolean connected = NetworkUtil.isConnected(context);
        //
        //        //请求在网络不可用的时候强制使用缓存
        //        if (!connected) {
        //            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        //         }
        //        return chain.proceed(request);
    }
}