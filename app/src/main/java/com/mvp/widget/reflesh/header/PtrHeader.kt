package com.happy.food.widget.reflesh.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.mvp.R
import com.mvp.widget.progress.CircularProgressView
import com.mvp.widget.reflesh.PtrFrameLayout
import com.mvp.widget.reflesh.PtrUIHandler
import com.mvp.widget.reflesh.indicator.PtrIndicator


/**
 * 项目名称：android
 * 类描述：
 * 创建人：admin
 * 创建时间：2018/1/9 17:05
 * 修改人：admin
 * 修改时间：2018/1/9 17:05
 * 修改备注：
 * @version
 */
class PtrHeader : FrameLayout, PtrUIHandler {

    private lateinit var circularProgressView: CircularProgressView
    private var headPaddingTop = 0

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    fun init(context: Context) {
        val inflater = LayoutInflater.from(context)
        val ptrView = inflater.inflate(R.layout.ptr_header, this)
        circularProgressView = ptrView.findViewById(R.id.progressBar)
        circularProgressView.isIndeterminate = false

        val layoutFooterParams = LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, resources.getDimension(R.dimen.dimen_60).toInt())
        layoutParams = layoutFooterParams

        headPaddingTop = resources.getDimension(R.dimen.dimen_10).toInt()
    }


    override fun onUIReset(frame: PtrFrameLayout?) {
        circularProgressView.progress = 0f
        circularProgressView.isIndeterminate = false
    }

    override fun onUIRefreshPrepare(frame: PtrFrameLayout?) {

    }

    override fun onUIRefreshBegin(frame: PtrFrameLayout?) {
        circularProgressView.isIndeterminate = true
    }

    override fun onUIRefreshComplete(frame: PtrFrameLayout?) {
        circularProgressView.progress = 0f
        circularProgressView.isIndeterminate = false
    }

    override fun onUIPositionChange(frame: PtrFrameLayout, isUnderTouch: Boolean, status: Byte, ptrIndicator: PtrIndicator) {
        val offsetToRefresh = frame.offsetToRefresh
        val currentPos = ptrIndicator.currentPosY
        val headerHeight = ptrIndicator.headerHeight
        val showHeadHeight = headerHeight - headPaddingTop
        val progressHead = offsetToRefresh - showHeadHeight

        if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            if (currentPos > showHeadHeight) {
                val progress = (currentPos - showHeadHeight) * 100f / progressHead
                if (progress < 100f) {
                    circularProgressView.progress = progress
                } else {
                    circularProgressView.progress = 100f
                }
            } else {
                circularProgressView.progress = 0f
            }
        }

    }

}