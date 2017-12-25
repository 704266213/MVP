package com.mvp.http.request

import com.happy.food.manager.NoCacheRetrofit
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.EntityResponse
import com.mvp.http.response.LoadMoreResponse
import com.mvp.model.BaseModel
import com.mvp.model.CinemaModel
import com.mvp.model.UserInfoModel
import com.mvp.view.base.ILoadMoreAndInitView
import com.mvp.view.base.InitView
import com.mvp.view.handler.LoadMoreHandler
import retrofit2.Call

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
class CinemaRequest(private var loadMoreAndInitView: ILoadMoreAndInitView<CinemaModel, List<CinemaModel>>?) : BaseEntityRequest<List<CinemaModel>>() {

//    private var call: Call<BaseModel<List<CinemaModel>>>? = null

    fun getUserInfoRequest(onLoadingViewListener: OnLoadingViewListener?, pageNo: String) {
        val cacheRetrofit = NoCacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getCinemaListInfo(pageNo)
        val loadMoreHandler = LoadMoreHandler(loadMoreAndInitView!!)
        var entityResponse = LoadMoreResponse(loadMoreHandler)
        val callBack = BaseResponseCallBack(onLoadingViewListener, entityResponse)
        call?.enqueue(callBack)
    }

    fun onDestroy() {
        loadMoreAndInitView = null
        call?.cancel()
    }
}