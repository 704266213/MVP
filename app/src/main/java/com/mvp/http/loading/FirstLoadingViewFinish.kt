package com.mvp.http.loading

/**
 * 项目名称：MVP
 * 类描述：
 * 创建人：admin
 * 创建时间：2017/12/28 15:14
 * 修改人：admin
 * 修改时间：2017/12/28 15:14
 * 修改备注：
 * @version
 */
class FirstLoadingViewFinish(onLoadingViewListener: OnLoadingViewListener?, private var onFirstLoadFinishListener: OnFirstLoadFinishListener) : LoadingView(onLoadingViewListener) {

    override fun showSuccessView() {
        onFirstLoadFinishListener.firstLoadFinish()
        super.showSuccessView()
    }

}