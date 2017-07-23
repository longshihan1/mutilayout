package com.longshihan.lh.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.longshihan.lh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author longshihan
 * @time 2017/7/21 16:47
 * @des
 */

//TODO: (1)实现对广义上的加载布局（2）加载弹窗上的布局（3） 加载指定位置上的布局
public class StatusLayout extends FrameLayout {
    private Context mContext;
    LayoutInflater inflater;
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


    //一个接口的三个实现，给父布局
    private SetLoadDataListener mSetLoadDataListener;

    private boolean isdialog;//判断是否要弹出框显示
    private boolean isPositionView;
    private View mPositionView;
    private Dialog mDialog;
    private List<View> usedviews = new ArrayList<>(2);

    private String ViewTAG = "ViewTag";


    public StatusLayout(@NonNull Context context) {
        this(context, null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        parseAttrs(context, attrs);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        currentShowingView = contentView;
    }

    /**
     * 加载默认视图,初始化
     *
     * @param context
     * @param attrs
     */
    private void parseAttrs(Context context, AttributeSet attrs) {
        inflater = LayoutInflater.from(context);
        contentView = findViewById(android.R.id.content);
        // TODO: 2017/7/23 空载实图
        emptyView = inflater.inflate(R.layout.empty, null);
        emptyContentView = emptyView.findViewById(R.id.emptycontent);
        emptyTextView = (TextView) emptyView.findViewById(R.id.emptytxt);
        emptyImageView = (ImageView) emptyView.findViewById(R.id.emptyimage);
        // TODO: 2017/7/23 错误视图
        errorView = inflater.inflate(R.layout.error, null);
        errorContentView = errorView.findViewById(R.id.errorcontent);
        errorTextView = (TextView) errorView.findViewById(R.id.errortxt);
        errorImageView = (ImageView) errorView.findViewById(R.id.errorimage);
        // TODO: 2017/7/23 加载中
        progressView = inflater.inflate(R.layout.progress, null);
    }

    /**
     * 配置 参数
     *
     * @param config
     */
    public void setLayoutConfig(BuildConfig config) {
        this.emptyView = config.mEmptyView;
        this.errorView = config.mErrorView;
        this.progressView = config.mLoadingErrorView;
        this.isdialog = config.isDialog;
        this.isPositionView = config.isPositionView;
        this.mPositionView = config.mPositionView;
    }

    /**
     * 配置空载布局的选项卡
     *
     * @param options
     */
    public void showView(CustomStateOptions options) {
        showCustomview(options);
    }

    private void showCustomview(CustomStateOptions options) {
        if (isdialog) {//存在dialog
            // TODO: 2017/7/21 dialog 这种不靠谱，先放下,不一定是dialog，可能是view外层有蒙版。有中间凸起

        } else if (isPositionView) {//在指定位置
            if (mPositionView != null) {//position的位置不为null
                ViewGroup.LayoutParams layoutParams = mPositionView.getLayoutParams();
                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) == mPositionView) {
                        getChildAt(i).setTag(ViewTAG);
                        getChildAt(i).setVisibility(GONE);
                    }
                }
                setCustomLayoutOption(options, layoutParams);
            } else {
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).setTag(ViewTAG);
                    getChildAt(i).setVisibility(GONE);
                }
                setCustomLayoutOption(options, null);
            }
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setTag(ViewTAG);
                getChildAt(i).setVisibility(GONE);
            }
            setCustomLayoutOption(options, null);
        }
    }

    private void setCustomLayoutOption(CustomStateOptions options, ViewGroup.LayoutParams
            layoutParams) {
        if (options.isEmpty()) {
            if (options.getViewRes() != 0) {
                emptyView = inflater.inflate(options.getViewRes(), null);
            } else {
                showViewOption(options, Status.EMPTY_VIEW);
            }
            if (layoutParams != null) {
                emptyView.setLayoutParams(layoutParams);
            }
            showContentParentView(emptyView);
        } else if (options.isError()) {
            if (options.getViewRes() != 0) {
                errorView = inflater.inflate(options.getViewRes(), null);
            } else {
                showViewOption(options, Status.ERROR_VIEW);
            }
            if (layoutParams != null) {
                errorView.setLayoutParams(layoutParams);
            }
            showContentParentView(errorView);
        } else if (options.isLoading()) {
            if (options.getViewRes() != 0) {
                progressView = inflater.inflate(options.getViewRes(), null);
            }
            if (layoutParams != null) {
                progressView.setLayoutParams(layoutParams);
            }
            showContentParentView(emptyView);
        }
    }

    private void showViewOption(final CustomStateOptions options, int type) {
        switch (type) {
            case Status.EMPTY_VIEW:
                if (options.getImageRes() != 0) {
                    emptyImageView.setBackgroundResource(options.getImageRes());
                }
                if (!TextUtils.isEmpty(options.getMessage())) {
                    emptyTextView.setText(options.getMessage());
                }
                if (options.getMessgaeRes() != 0) {
                    emptyTextView.setText(options.getMessgaeRes());
                }

                switch (options.getListenertype()) {
                    case Status.CONTENTCLICK:
                        emptyContentView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("TAG","点击");
                                Toast.makeText(mContext, "点击", Toast.LENGTH_SHORT).show();
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.TEXTCLICK:
                        emptyTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.IMAGECLICK:
                        emptyImageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.BUTTONCLICK:
                        emptyTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    default:
                        break;
                }
                break;
            case Status.ERROR_VIEW:
                if (options.getImageRes() != 0) {
                    errorImageView.setBackgroundResource(options.getImageRes());
                }
                if (!TextUtils.isEmpty(options.getMessage())) {
                    errorTextView.setText(options.getMessage());
                }
                if (options.getMessgaeRes() != 0) {
                    errorTextView.setText(options.getMessgaeRes());
                }
                switch (options.getListenertype()) {
                    case Status.CONTENTCLICK:
                        errorContentView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.TEXTCLICK:
                        errorTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.IMAGECLICK:
                        errorImageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    case Status.BUTTONCLICK:
                        errorTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                options.getButtonClickListener().onClick(v);
                            }
                        });
                        break;
                    default:
                        break;
                }
                break;
            case Status.PROGRESS_VIEW:
                break;
        }
    }

    /**
     * 加载空载view，但事件处理全在上层
     */
    public void showEmptyView() {
        showCustomview(new CustomStateOptions().empty());
    }

    public void showErrorView() {
        showCustomview(new CustomStateOptions().error());
    }

    public void showLoadView() {
        showCustomview(new CustomStateOptions().loading());
    }

    /**
     * 处理视图的显示和加载
     *
     * @param view
     */
    private void showContentParentView(View view) {
        if (usedviews.size() > 0) {
            for (int i = 0; i < usedviews.size(); i++) {
                removeView(usedviews.get(i));
            }
        }
        usedviews.add(view);
        addView(view);
        view.setVisibility(VISIBLE);
    }

    public void cleanallView() {
        if (usedviews.size() > 0) {
            for (int i = 0; i < usedviews.size(); i++) {
                removeView(usedviews.get(i));
            }
        }
        for (int i = 0; i < getChildCount(); i++) {
            if (ViewTAG.equals(getChildAt(i).getTag())) {
                getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }

    public TextView getEmptyTextView() {
        return emptyTextView;
    }

    public void setEmptyTextView(TextView emptyTextView) {
        this.emptyTextView = emptyTextView;
    }

    public TextView getErrorTextView() {
        return errorTextView;
    }

    public void setErrorTextView(TextView errorTextView) {
        this.errorTextView = errorTextView;
    }

    public ImageView getErrorImageView() {
        return errorImageView;
    }

    public void setErrorImageView(ImageView errorImageView) {
        this.errorImageView = errorImageView;
    }

    public ImageView getEmptyImageView() {
        return emptyImageView;
    }

    public void setEmptyImageView(ImageView emptyImageView) {
        this.emptyImageView = emptyImageView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
