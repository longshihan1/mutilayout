package com.longshihan.lh.ui;

import android.content.Context;
import android.view.View;

public class BuildConfig  {
    public Context mContext;
    public View mEmptyView;
    public View mErrorView;
    public View mLoadingErrorView;
    public boolean isDialog;
    public boolean isPositionView;
    public View mPositionView;
    public boolean isguild;

    public boolean isguild() {
        return isguild;
    }

    public BuildConfig setIsguild(boolean isguild) {
        this.isguild = isguild;
        return this;
    }

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

    public BuildConfig setDialog(boolean isDialog) {
        this.isDialog = isDialog;
        return this;
    }

    public BuildConfig setPosition(boolean isPositionView) {
        this.isPositionView = isPositionView;
        return this;
    }

    public BuildConfig setPositionView(View mPositionView) {
        this.mPositionView = mPositionView;
        return this;
    }
}
