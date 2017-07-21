package com.longshihan.lh.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longshihan
 * @time 2017/7/21 16:47
 * @des
 */

//TODO: (1)实现对广义上的加载布局（2）加载弹窗上的布局（3） 加载指定位置上的布局
public class StatusLayout extends FrameLayout {
    private View contentView;//当前布局
    private View emptyView;//空载布局
    private View errorView;//错误布局
    private View progressView;//加载中布局
    private View currentShowingView;//当前布局

    private View emptyContentView;
    private View errorContentView;
    private View progressContentView;

    private TextView emptyTextView;
    private TextView errorTextView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ProgressBar progressBar;

    private boolean isdialog;//判断是否要弹出框显示
    private boolean isPositionView;
    private View mPositionView;
    private Dialog mDialog;
    private List<View> usedviews = new ArrayList<>(2);


    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        parseAttrs(context, attrs);

        emptyView.setVisibility(View.GONE);

        errorView.setVisibility(View.GONE);

        progressView.setVisibility(View.GONE);

        currentShowingView = contentView;
    }

    /**
     * 加载默认视图
     *
     * @param context
     * @param attrs
     */
    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);

    }

    public void setLayoutConfig(BuildConfig config) {
        this.emptyView = config.mEmptyView;
        this.errorView = config.mErrorView;
        this.progressView = config.mLoadingErrorView;
        this.isdialog = config.isDialog;
        this.isPositionView = config.isPositionView;
        this.mPositionView = config.mPositionView;
    }

    /**
     * 加载空载view，但事件处理全在上层
     */
    public void showEmptyView() {
        if (isdialog) {//存在dialog
            // TODO: 2017/7/21 dialog 这种不靠谱，先放下,不一定是dialog，可能是view外层有蒙版。有中间凸起

        } else if (isPositionView) {//在指定位置
            if (mPositionView != null) {//position的位置不为null
                ViewGroup.LayoutParams layoutParams = mPositionView.getLayoutParams();
                emptyView.setLayoutParams(layoutParams);
                showContentParentView(emptyView);
            } else {
                showContentParentView(emptyView);
            }

        }
    }

    public void showErrorView() {
        if (isdialog) {//存在dialog
            // TODO: 2017/7/21 dialog 这种不靠谱，先放下,不一定是dialog，可能是view外层有蒙版。有中间凸起

        } else if (isPositionView) {//在指定位置
            if (mPositionView != null) {//position的位置不为null
                ViewGroup.LayoutParams layoutParams = mPositionView.getLayoutParams();
                errorView.setLayoutParams(layoutParams);
                showContentParentView(errorView);
            } else {
                showContentParentView(errorView);
            }

        }
    }

    public void showLoadView() {
        if (isdialog) {//存在dialog
            // TODO: 2017/7/21 dialog 这种不靠谱，先放下,不一定是dialog，可能是view外层有蒙版。有中间凸起

        } else if (isPositionView) {//在指定位置
            if (mPositionView != null) {//position的位置不为null
                ViewGroup.LayoutParams layoutParams = mPositionView.getLayoutParams();
                progressView.setLayoutParams(layoutParams);
                showContentParentView(progressView);
            } else {
                showContentParentView(progressView);
            }

        }
    }

    private void showContentParentView(View view) {
        if (usedviews.size() > 0) {
            for (int i = 0; i < usedviews.size(); i++) {
                removeView(usedviews.get(i));
            }
        }
        usedviews.add(view);
        addView(view);
    }

    public void cleanallView() {

    }

    public static final class BuildConfig {
        private Context mContext;
        private View mEmptyView;
        private View mErrorView;
        private View mLoadingErrorView;
        private boolean isDialog;
        private boolean isPositionView;
        private View mPositionView;

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
}
