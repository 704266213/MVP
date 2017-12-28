package com.mvp.presenter

import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.request.BaseEntityRequest
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.RefreshLoadMoreResponse
import com.mvp.http.response.listener.OnResponseListener
import com.mvp.http.retrofit.CacheRetrofit
import com.mvp.model.FilmInfoModel
import com.mvp.model.HotFilmModel
import com.mvp.view.FilmListInfoView

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
class GetFilmListInfoPresenter(private var filmListInfoView: FilmListInfoView<FilmInfoModel, List<FilmInfoModel>>?) : BaseEntityRequest<HotFilmModel>(), OnResponseListener<HotFilmModel> {

    private var refreshLoadMoreResponse = RefreshLoadMoreResponse(filmListInfoView)

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
            refreshLoadMoreResponse.onSuccess(filmModels)
        }
        val bannerModels = entity?.bannerModels
        if (bannerModels != null) {
            filmListInfoView?.initBanner(bannerModels)
        }
    }

    override fun onFailure(errorInfo: String?) {
        if (filmListInfoView != null) {
            refreshLoadMoreResponse.onFailure(errorInfo)
        }
    }


}