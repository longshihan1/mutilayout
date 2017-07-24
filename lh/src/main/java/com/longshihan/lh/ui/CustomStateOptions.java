package com.longshihan.lh.ui;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;


public class CustomStateOptions {
    @DrawableRes
    private int imageRes;
    private String message;
    @IdRes
    private int messgaeRes;
    private String buttonText;
    @IdRes
    private int buttonTextRes;
    @LayoutRes
    private int viewRes;

    private boolean isLoading;
    private boolean isEmpty;
    private boolean isError;
    private int listenertype=Status.CONTENTCLICK;
    private View.OnClickListener buttonClickListener;

    public CustomStateOptions image(@DrawableRes int val) {
        imageRes = val;
        return this;
    }

    public CustomStateOptions message(String val) {
        message = val;
        return this;
    }

    public CustomStateOptions buttonText(String val) {
        buttonText = val;
        return this;
    }

    public CustomStateOptions buttonRes(@IdRes int buttonTextRes) {
        this.buttonTextRes = buttonTextRes;
        return this;
    }

    public CustomStateOptions messageRes(@IdRes int messgaeRes) {
        this.messgaeRes = messgaeRes;
        return this;
    }

    public CustomStateOptions image(@DrawableRes int val,View.OnClickListener valListener) {
        buttonClickListener = valListener;
        listenertype=Status.IMAGECLICK;
        imageRes = val;
        return this;
    }

    public CustomStateOptions message(String val,View.OnClickListener valListener) {
        buttonClickListener = valListener;
        listenertype=Status.TEXTCLICK;
        message = val;
        return this;
    }

    public CustomStateOptions buttonText(String val,View.OnClickListener valListener) {
        buttonClickListener = valListener;
        listenertype=Status.BUTTONCLICK;
        buttonText = val;
        return this;
    }

    public CustomStateOptions buttonRes(@IdRes int buttonTextRes,View.OnClickListener valListener) {
        buttonClickListener = valListener;
        listenertype=Status.BUTTONCLICK;
        this.buttonTextRes = buttonTextRes;
        return this;
    }

    public CustomStateOptions messageRes(@IdRes int messgaeRes,View.OnClickListener valListener) {
        buttonClickListener = valListener;
        listenertype=Status.TEXTCLICK;
        this.messgaeRes = messgaeRes;
        return this;
    }

    public CustomStateOptions layoutRes(@LayoutRes int layoutres) {
        this.viewRes = layoutres;
        return this;
    }

    public CustomStateOptions buttonClickListener(View.OnClickListener val) {
        buttonClickListener = val;
        listenertype=Status.CONTENTCLICK;
        return this;
    }

    public CustomStateOptions empty() {
        isEmpty = true;
        return this;
    }
    public CustomStateOptions error() {
        isError = true;
        return this;
    }
    public CustomStateOptions loading() {
        isLoading = true;
        return this;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isError() {
        return isError;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getMessage() {
        return message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public View.OnClickListener getButtonClickListener() {
        return buttonClickListener;
    }

    public int getMessgaeRes() {
        return messgaeRes;
    }

    public int getButtonTextRes() {
        return buttonTextRes;
    }

    public int getViewRes() {
        return viewRes;
    }

    public int getListenertype() {
        return listenertype;
    }
}
