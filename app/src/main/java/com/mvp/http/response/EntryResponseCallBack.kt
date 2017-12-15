package com.mvp.http.response

import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.response.listener.OnEntryResponseListener
import com.mvp.model.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 18:05
 * 修改人：admin
 * 修改时间：2017/12/13 18:05
 * 修改备注：
 * @version
 */
class EntryResponseCallBack<E, T : BaseModel<List<E>>>(private val onLoadingViewListener: OnLoadingViewListener?,
                                                       private val onEntryResponseListener: OnEntryResponseListener<E, List<E>>) : Callback<T> {

    init {
        onLoadingViewListener?.showLoadingView()
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        val result = response?.body()
        if (result != null) {
            if (result.state == 200) {
                var list :List<E>? = result.result
                onEntryResponseListener.onSuccess(result.result)
                onLoadingViewListener?.showSuccessView()
            } else {
                onEntryResponseListener.onFailure(result.message)
                onLoadingViewListener?.showErrorView()
            }
        }
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        onLoadingViewListener?.showErrorView()
        onEntryResponseListener.onFailure(t.toString())
    }

}