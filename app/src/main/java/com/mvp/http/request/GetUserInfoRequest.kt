package com.mvp.http.request

import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.EntityResponse
import com.mvp.http.retrofit.CacheRetrofit
import com.mvp.model.BaseModel
import com.mvp.model.HotFilmModel
import com.mvp.model.UserInfoModel
import com.mvp.view.InitView
import retrofit2.Call

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/22 11:04
 * 修改人：admin
 * 修改时间：2017/12/22 11:04
 * 修改备注：
 * @version
 */
class GetUserInfoRequest(private var initView: InitView<UserInfoModel>?) : BaseEntityRequest() {

    private var call: Call<BaseModel<UserInfoModel>>? = null

    fun getUserInfoRequest(onLoadingViewListener: OnLoadingViewListener?, userInfo: String) {
        val cacheRetrofit = CacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getUserInfo(userInfo)
        var entityResponse = EntityResponse(initView)
        val callBack = BaseResponseCallBack(onLoadingViewListener, entityResponse)
        call?.enqueue(callBack)
    }

    fun onDestroy() {
        initView = null
        call?.cancel()
    }

}