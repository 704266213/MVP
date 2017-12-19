package com.mvp.api

import com.mvp.model.BaseModel
import com.mvp.model.FilmInfoModel
import com.mvp.model.HotFilmModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


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

}
