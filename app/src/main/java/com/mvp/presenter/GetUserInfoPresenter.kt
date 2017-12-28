package com.mvp.presenter

import com.happy.food.manager.NoCacheRetrofit
import com.mvp.http.loading.OnLoadingViewListener
import com.mvp.http.request.BaseEntityRequest
import com.mvp.http.response.BaseResponseCallBack
import com.mvp.http.response.BaseEntityResponse
import com.mvp.model.UserInfoModel
import com.mvp.view.base.BaseEntityView

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
class GetUserInfoPresenter(private var baseEntityView: BaseEntityView<UserInfoModel>?) : BaseEntityRequest<UserInfoModel>() {


    fun getUserInfoRequest(onLoadingViewListener: OnLoadingViewListener?, userInfo: String) {
        val cacheRetrofit = NoCacheRetrofit()
        val apiService = createApiService(cacheRetrofit)
        call = apiService.getUserInfo(userInfo)
        var entityResponse = BaseEntityResponse(baseEntityView)
        val callBack = BaseResponseCallBack(onLoadingViewListener, entityResponse)
        call?.enqueue(callBack)
    }


}