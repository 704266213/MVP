package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.LoadingView
import com.mvp.presenter.GetUserInfoPresenter
import com.mvp.model.UserInfoModel
import com.mvp.view.base.BaseEntityView
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity(), OnStartRequestListener, BaseEntityView<UserInfoModel> {


    private var loadingView: LoadingView? = null
    private var getUserInfoRequest = GetUserInfoPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        initView()
    }

    private fun initView() {
        loadingViewLayout.onStartRequestListener = this
        loadingView = LoadingView(loadingViewLayout)

        startRequest()
    }

    override fun startRequest() {
        getUserInfoRequest.getUserInfoRequest(loadingView, "userinfo.json")
    }

    override fun onSuccess(entity: UserInfoModel?) {
        result.visibility = View.VISIBLE
        if (entity != null)
            result.text = "{  userName：${entity.userName},  sex : ${entity.sex},  headUrl: ${entity.headUrl} }"
    }

    override fun onFailure(errorInfo: String?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        getUserInfoRequest.onDestroy()
    }
}
