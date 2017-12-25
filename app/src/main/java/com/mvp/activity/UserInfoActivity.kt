package com.mvp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mvp.R
import com.mvp.http.listener.OnStartRequestListener
import com.mvp.http.loading.LoadingView
import com.mvp.http.request.GetUserInfoRequest
import com.mvp.model.UserInfoModel
import com.mvp.view.base.InitView
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity(), OnStartRequestListener, InitView<UserInfoModel> {


    private var loadingView: LoadingView? = null
    private var getUserInfoRequest = GetUserInfoRequest(this)


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

    override fun initViewSuccess(entity: UserInfoModel?) {
        result.visibility = View.VISIBLE
        if (entity != null)
            result.text = "{  userName：${entity.userName},  sex : ${entity.sex},  headUrl: ${entity.headUrl} }"
    }

    override fun initViewFail(errorInfo: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        getUserInfoRequest.onDestroy()
    }
}
