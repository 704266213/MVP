package com.mvp.http.request

import android.util.Log
import com.happy.food.manager.NoCacheRetrofit
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.http.retrofit.CacheRetrofit
import com.mvp.model.BaseModel
import com.mvp.model.HotFilmModel
import com.mvp.view.FilmListInfoView
import com.mvp.view.handler.RefreshAndLoadMoreHandler
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
class GetFilmListInfoRequest(private var filmListInfoView: FilmListInfoView?) : BaseEntityRequest(), OnResponseListener<HotFilmModel> {

    private var call: Call<BaseModel<HotFilmModel>>? = null
    private var refreshAndLoadMoreHandler = RefreshAndLoadMoreHandler(filmListInfoView!!)

    fun getFilmListInfoRequest(onLoadingViewListener: OnLoadingViewListener?, pageNo: String) {
        val cacheRetrofit = CacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getFilmListInfo(pageNo)
        val callBack = BaseResponseCallBack(onLoadingViewListener, this)
        call?.enqueue(callBack)
    }

    override fun onSuccess(entity: HotFilmModel?) {
        val filmModels = entity?.filmModels
        if (filmModels != null) {
            refreshAndLoadMoreHandler.onSuccessHandler(filmModels)
        }
        val bannerModels = entity?.bannerModels
        if (bannerModels != null) {
            filmListInfoView?.initBanner(bannerModels)
        }
    }

    override fun onFailure(errorInfo: String) {
        if (filmListInfoView != null) {
            refreshAndLoadMoreHandler.onFailureHandler(errorInfo)
        }
    }

    fun onDestroy() {
        filmListInfoView = null
        call?.cancel()
    }


}