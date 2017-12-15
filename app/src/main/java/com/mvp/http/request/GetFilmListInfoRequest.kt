package com.mvp.http.request

import android.util.Log
import com.happy.food.manager.NoCacheRetrofit
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.model.BaseModel
import com.mvp.model.FilmInfoModel
import retrofit2.Call

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/13 10:56
 * 修改人：admin
 * 修改时间：2017/12/13 10:56
 * 修改备注：
 * @version
 */
class GetFilmListInfoRequest(onResponseListener: OnResponseListener<List<FilmInfoModel>>) : BaseEntityRequest<List<FilmInfoModel>>(onResponseListener) {

    private var call: Call<BaseModel<List<FilmInfoModel>>>? = null

    fun getFilmListInfoRequest(onLoadingViewListener: OnLoadingViewListener?, pageNo: String) {
        val cacheRetrofit = NoCacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getFilmListInfo(pageNo)
        val callBack = BaseResponseCallBack(onLoadingViewListener, this)
        call?.enqueue(callBack)
    }

    override fun onDestroy() {
        call?.cancel()
        super.onDestroy()
    }


    override fun onSuccess(entity: List<FilmInfoModel>?) {
        Log.e("XLog", "==============onSuccess=======================")
        super.onSuccess(entity)
    }

    override fun onFailure(errorInfo: String) {
        Log.e("XLog", "==============onFailure=======================")
        super.onFailure(errorInfo)
    }


}