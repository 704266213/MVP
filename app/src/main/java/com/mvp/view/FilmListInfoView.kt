package com.mvp.view

import com.mvp.model.BannerModel
import com.mvp.model.FilmInfoModel
import com.mvp.view.base.IRefreshAndLoadMoreView

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/19 10:24
 * 修改人：admin
 * 修改时间：2017/12/19 10:24
 * 修改备注：
 * @version
 */
interface FilmListInfoView : IRefreshAndLoadMoreView<FilmInfoModel, List<FilmInfoModel>> {

    fun initBanner(banners: List<BannerModel>)

}