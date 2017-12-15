package com.mvp.http.retrofit

import com.mvp.BuildConfig
import com.mvp.http.converter.FastJsonConverterFactory
import com.mvp.http.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 10:15
 * 修改人：admin
 * 修改时间：2017/12/13 10:15
 * 修改备注：
 * @version
 */
abstract class BaseRetrofit {

    internal val interceptor = HttpLoggingInterceptor()

    init {
        val level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        interceptor.setLevel(level)
    }

    //1.创建Retrofit对象
    fun createRetrofit(): Retrofit = Retrofit.Builder()
            //设置OKHttpClient,如果不设置会提供一个默认的
            .client(createOkHttpClient())
            //设置baseUrl
            .baseUrl("https://raw.githubusercontent.com/704266213/")
            //添加fastJson转换器
            .addConverterFactory(FastJsonConverterFactory.create())
            .build()


    abstract fun createOkHttpClient(): OkHttpClient
}