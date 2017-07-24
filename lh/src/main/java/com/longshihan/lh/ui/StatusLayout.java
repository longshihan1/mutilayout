package com.longshihan.lh.ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.longshihan.lh.R;

import java.util.ArrayList;
import java.util.List;


public class StatusLayout extends FrameLayout {
    private Context mContext;
    LayoutInflater inflater;
    private View contentView;
    private View emptyView;
    private View errorView;
    private View progressView;
    private View currentShowingView;
    private View mflipperview;
    private View mStubView;
    private ViewStub mGuideView;

    private View emptyContentView;
    private View errorContentView;
    private View progressContentView;

    private TextView emptyTextView;
    private TextView errorTextView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ProgressBar progressBar;

    private ViewFlipper mViewFlipper;

    private CustomStateOptions emptyoptions;
    private CustomStateOptions erroroptions;
    private CustomStateOptions progressoptions;
    private CustomGuildeOptions guildeoptions;



    private SetLoadDataListener mSetLoadDataListener;

    private boolean isdialog;
    private boolean isPositionView;
    private boolean isguilde;
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

    private void parseAttrs(Context context, AttributeSet attrs) {
        inflater = LayoutInflater.from(context);
        contentView = findViewById(android.R.id.content);
        /*-----------------------------------------------------------------------------------------*/
        emptyView = inflater.inflate(R.layout.empty, null);
        emptyContentView = emptyView.findViewById(R.id.emptycontent);
        emptyTextView = (TextView) emptyView.findViewById(R.id.emptytxt);
        emptyImageView = (ImageView) emptyView.findViewById(R.id.emptyimage);
        /*-----------------------------------------------------------------------------------------*/
        errorView = inflater.inflate(R.layout.error, null);
        errorContentView = errorView.findViewById(R.id.errorcontent);
        errorTextView = (TextView) errorView.findViewById(R.id.errortxt);
        errorImageView = (ImageView) errorView.findViewById(R.id.errorimage);
        /*-----------------------------------------------------------------------------------------*/
        progressView = inflater.inflate(R.layout.progress, null);
        /*-----------------------------------------------------------------------------------------*/
        mflipperview = inflater.inflate(R.layout.viewstubflipper, null);
        mGuideView = (ViewStub) mflipperview.findViewById(R.id.stubid);
    }

    public void setLayoutConfig(BuildConfig config) {
        if (config.mEmptyView != null) {
            this.emptyView = config.mEmptyView;
        }
        if (config.mErrorView != null) {
            this.errorView = config.mErrorView;
        }
        if (config.mLoadingErrorView != null) {
            this.progressView = config.mLoadingErrorView;
        }
        this.isdialog = config.isDialog;
        this.isPositionView = config.isPositionView;
        this.mPositionView = config.mPositionView;
        this.isguilde = config.isguild;
    }

    public void showView(CustomStateOptions options) {
        showCustomview(options);
    }

    public void showGuildeView(final CustomGuildeOptions options) {
        if (mStubView == null) {
            mStubView = mGuideView.inflate();
            mViewFlipper = (ViewFlipper) mStubView.findViewById(R.id.glideviewflipper);
        }
        guildeoptions = options;
        if (mViewFlipper.getChildCount() == 0) {
            for (int i = 0; i < options.getViews().size(); i++) {
                View view = options.getViews().get(i);
                mViewFlipper.addView(view);
                final int finalI = i;
                View currentid;
                if (view.getTag(R.id.ids) != null) {
                    try {
                        currentid = view.findViewById(Integer.valueOf(view.getTag(R.id.ids).toString()));
                    } catch (Exception e) {
                        currentid = view;
                    }
                } else {
                    currentid = view;
                }
                currentid.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalI == options.getViews().size() - 1) {
                            cleanallView();
                        } else {
                            mViewFlipper.showNext();
                        }
                    }
                });
            }
        }


    }

    public void showGuideView() {
        showGuildeView(guildeoptions);
        cleanShowView();
        addView(mflipperview);
        usedviews.add(mflipperview);
        mflipperview.setVisibility(VISIBLE);
    }

    private void showCustomview(CustomStateOptions options) {
        if (isdialog) {

        } else if (isPositionView) {
            if (mPositionView != null) {
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
            emptyoptions = options;
            if (options.getViewRes() != 0) {
                emptyView = inflater.inflate(options.getViewRes(), null);
            } else {
                showViewOption(options, Status.EMPTY_VIEW);
            }
            if (layoutParams != null) {
                emptyView.setLayoutParams(layoutParams);
            }
        } else if (options.isError()) {
            erroroptions = options;
            if (options.getViewRes() != 0) {
                errorView = inflater.inflate(options.getViewRes(), null);
            } else {
                showViewOption(options, Status.ERROR_VIEW);
            }
            if (layoutParams != null) {
                errorView.setLayoutParams(layoutParams);
            }

        } else if (options.isLoading()) {
            progressoptions = options;
            if (options.getViewRes() != 0) {
                progressView = inflater.inflate(options.getViewRes(), null);
            }
            if (layoutParams != null) {
                progressView.setLayoutParams(layoutParams);
            }

        }
    }

    private void showViewOption(final CustomStateOptions options, int type) {
        if (mOnClickListener != null && mOnClickListener == null) {
            setOnClick(options.getButtonClickListener());
        }
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
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.TEXTCLICK:
                        emptyTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.IMAGECLICK:
                        emptyImageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.BUTTONCLICK:
                        emptyTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
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
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.TEXTCLICK:
                        errorTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.IMAGECLICK:
                        errorImageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
                            }
                        });
                        break;
                    case Status.BUTTONCLICK:
                        errorTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnClickListener.onClick(v);
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

    public void showEmptyView() {
        if (emptyoptions != null) {
            showCustomview(emptyoptions);
        } else {
            showCustomview(new CustomStateOptions().empty());
        }
        showContentParentView(emptyView);
    }

    public void showErrorView() {
        if (erroroptions != null) {
            showCustomview(erroroptions);
        } else {
            showCustomview(new CustomStateOptions().error());
        }
        showContentParentView(errorView);
    }

    public void showLoadView() {
        if (progressoptions != null) {
            showCustomview(progressoptions);
        } else {
            showCustomview(new CustomStateOptions().loading());
        }
        showContentParentView(progressView);
    }

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

    public void cleanShowView() {
        if (usedviews.size() > 0) {
            for (int i = 0; i < usedviews.size(); i++) {
                removeView(usedviews.get(i));
            }
        }
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

    private View.OnClickListener mOnClickListener;

    private void setOnClick(View.OnClickListener mClick) {
        mOnClickListener = mClick;
    }

    private View.OnClickListener mGuildeOnClickListener;

    private void setGuildeOnClick(View.OnClickListener mClick) {
        mGuildeOnClickListener = mClick;
    }
}
