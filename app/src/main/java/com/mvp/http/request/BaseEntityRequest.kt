package com.mvp.http.request

import com.mvp.api.ApiService
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.http.retrofit.BaseRetrofit

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 10:59
 * 修改人：admin
 * 修改时间：2017/12/13 10:59
 * 修改备注：
 * @version
 */
open abstract class BaseEntityRequest<in T>(private var onResponseListener: OnResponseListener<T>?) : OnResponseListener<T> {

    fun createApiService(baseRetrofit: BaseRetrofit): ApiService {
        val retrofit = baseRetrofit.createRetrofit()
        return retrofit.create(ApiService::class.java)
    }

    override fun onSuccess(entity: T?) {
        onResponseListener?.onSuccess(entity)
    }

    override fun onFailure(errorInfo: String) {
        onResponseListener?.onFailure(errorInfo)
    }

    open fun onDestroy() {
        onResponseListener = null
    }

}