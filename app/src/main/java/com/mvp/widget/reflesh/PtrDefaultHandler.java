package com.mvp.widget.reflesh;

import android.view.View;
import android.widget.AbsListView;

public class PtrDefaultHandler implements PtrHandler {

    private OnRefreshBeginListener onRefreshBeginListener;

    public PtrDefaultHandler(OnRefreshBeginListener onRefreshBeginListener) {
        this.onRefreshBeginListener = onRefreshBeginListener;
    }


    public boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (onRefreshBeginListener != null) {
            onRefreshBeginListener.onRefreshBeginListener();
        }
    }
}