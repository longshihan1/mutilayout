package com.longshihan.lh.ui;

import android.content.Context;
import android.view.View;

/**
 * 主view上加载空载。错误，加载视图选项，
 * 未使用则默认
 */

public class BuildConfig {
    public Context mContext;
    public View mEmptyView;
    public View mErrorView;
    public View mLoadingErrorView;
    public boolean isDialog;
    public boolean isPositionView;
    public View mPositionView;

    public BuildConfig(Context context) {
        this.mContext = context;
    }

    public BuildConfig emptyView(View emptyView) {
        mEmptyView = emptyView;
        return this;
    }

    public BuildConfig networkErrorView(View networkErrorView) {
        mErrorView = networkErrorView;
        return this;
    }

    public BuildConfig otherErrorView(View otherErrorView) {
        mLoadingErrorView = otherErrorView;
        return this;
    }

    /**
     * 是否弹窗显示
     *
     * @param isDialog
     * @return
     */
    public BuildConfig setDialog(boolean isDialog) {
        this.isDialog = isDialog;
        return this;
    }

    /**
     * 是否在指定位置显示
     *
     * @param isPositionView
     * @return
     */
    public BuildConfig setPosition(boolean isPositionView) {
        this.isPositionView = isPositionView;
        return this;
    }


    /**
     * 设置指定要替换的view
     *
     * @param mPositionView
     * @return
     */
    public BuildConfig setPositionView(View mPositionView) {
        this.mPositionView = mPositionView;
        return this;
    }
}
