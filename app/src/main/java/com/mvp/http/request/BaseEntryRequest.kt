package com.mvp.http.request

import com.mvp.adapter.listener.AddDataToAdapterListener
import com.mvp.api.ApiService
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.http.retrofit.BaseRetrofit

/**
 * 项目名称：MVP
 * 类描述：用于处理返回的结果为数组对象的情况
 * 创建人：admin
 * 创建时间：2017/12/14 9:12
 * 修改人：admin
 * 修改时间：2017/12/14 9:12
 * 修改备注：
 * @version
 */
class BaseEntryRequest<out E, in T : List<E>>(private var onResponseListener: OnResponseListener<T>?, private var addDataToAdapterListener: AddDataToAdapterListener<E>) : OnResponseListener<T> {

    fun createApiService(baseRetrofit: BaseRetrofit): ApiService {
        val retrofit = baseRetrofit.createRetrofit()
        return retrofit.create(ApiService::class.java)
    }

    override fun onSuccess(entity: T?) {
        onResponseListener?.onSuccess(entity)
        var list: List<E> = entity as List<E>
        addDataToAdapterListener.addDataToList(list)
    }

    override fun onFailure(errorInfo: String) {
        onResponseListener?.onFailure(errorInfo)
    }

    fun onDestroy() {
        onResponseListener = null
    }

}