package com.mvp.http.response

import android.util.Log
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.model.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 9:33
 * 修改人：admin
 * 修改时间：2017/12/13 9:33
 * 修改备注：
 * @version
 */
class BaseResponseCallBack<E, T : BaseModel<E>>(private val onLoadingViewListener: OnLoadingViewListener?, private val onResponseListener: OnResponseListener<E>) : Callback<T> {

    init {
        onLoadingViewListener?.showLoadingView()
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        val result = response?.body()
        if (result != null) {
            if (result.state == 200) {
                onResponseListener.onSuccess(result.result)
                onLoadingViewListener?.showSuccessView()
            } else {
                onResponseListener.onFailure(result.message)
                onLoadingViewListener?.showErrorView()
            }
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        var isCancel = call?.isCanceled ?: false
        if (isCancel) {
            Log.e("XLog", "request is canceled")
        } else {
            Log.e("XLog", "error:" + t?.message)
        }
        onLoadingViewListener?.showErrorView()
        onResponseListener.onFailure(t.toString())
    }
}