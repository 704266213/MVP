package com.mvp.widget.reflesh;

import android.content.Context;
import android.util.AttributeSet;

import com.happy.food.widget.reflesh.header.PtrHeader;

public class PtrClassicFrameLayout extends PtrFrameLayout {

    private PtrClassicDefaultHeader mPtrClassicHeader;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
//        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
//        setHeaderView(mPtrClassicHeader);
//        addPtrUIHandler(mPtrClassicHeader);

        PtrHeader ptrHeader = new PtrHeader(getContext());
        setHeaderView(ptrHeader);
        addPtrUIHandler(ptrHeader);
    }


}
