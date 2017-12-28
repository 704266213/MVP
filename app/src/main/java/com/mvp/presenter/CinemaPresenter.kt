package com.mvp.presenter

import com.happy.food.manager.NoCacheRetrofit
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.request.BaseEntityRequest
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.LoadMoreResponse
import com.mvp.model.CinemaModel
import com.mvp.view.base.LoadMoreView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/25 11:40
 * 修改人：admin
 * 修改时间：2017/12/25 11:40
 * 修改备注：
 * @version
 */
class CinemaPresenter(private var loadMoreView: LoadMoreView<CinemaModel, List<CinemaModel>>?) : BaseEntityRequest<List<CinemaModel>>() {


    fun getUserInfoRequest(onLoadingViewListener: OnLoadingViewListener?, pageNo: String) {
        val cacheRetrofit = NoCacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getCinemaListInfo(pageNo)
        var entityResponse = LoadMoreResponse(loadMoreView)
        val callBack = BaseResponseCallBack(onLoadingViewListener, entityResponse)
        call?.enqueue(callBack)
    }


}