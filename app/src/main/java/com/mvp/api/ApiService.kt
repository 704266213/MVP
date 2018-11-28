package com.mvp.api

import com.mvp.model.*
import retrofit2.Call
import retrofit2.http.*


/**
 * 项目名称：App
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/8/17 13:49
 * 修改人：admin
 * 修改时间：2017/8/17 13:49
 * 修改备注：
 */

interface ApiService {

    @GET("data/master/WebContent/data/{pageNo}")
    fun getFilmListInfo(@Path("pageNo") pageNo: String): Call<BaseModel<HotFilmModel>>

    @GET("data/master/WebContent/data/{userInfo}")
    fun getUserInfo(@Path("userInfo") userInfo: String): Call<BaseModel<UserInfoModel>>

    @GET("data/master/WebContent/data/{pageNo}")
    fun getCinemaListInfo(@Path("pageNo") pageNo: String): Call<BaseModel<List<CinemaModel>>>

    @FormUrlEncoded
    @POST("data/master/WebContent/data/{pageNo}")
    fun  getInfo(@Path("pageNo") pageNo: String, @FieldMap fieldMap: Map<String, String>): Call<BaseModel<UserInfoModel>>

}
