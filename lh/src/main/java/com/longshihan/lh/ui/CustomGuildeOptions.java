package com.longshihan.lh.ui;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

import com.longshihan.lh.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author longshihan
 * @time 2017/7/24 11:43
 * @des
 */

public class CustomGuildeOptions {
    private List<View> mViews;
    private Context mContext;
    private LayoutInflater mInflater;
    private Map<String,Integer> map;

    public CustomGuildeOptions(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mViews = new ArrayList<>();
    }

    public CustomGuildeOptions appendView(@LayoutRes int viewids) {
        View currentview = mInflater.inflate(viewids, null);
        mViews.add(currentview);
        return this;
    }

    public CustomGuildeOptions appendView(View view) {
        mViews.add(view);
        return this;
    }

    public CustomGuildeOptions appendView(@LayoutRes int viewids, @IdRes int ids) {
        View currentview = mInflater.inflate(viewids, null);
        currentview.setTag(R.id.ids,ids);
        mViews.add(currentview);
        return this;
    }

    public CustomGuildeOptions appendView(View view, int ids) {
        view.setTag(R.id.ids, ids);
        mViews.add(view);
        return this;
    }

    public List<View> getViews() {
        return mViews;
    }

    public CustomGuildeOptions setViews(List<View> views) {
        mViews = views;
        return this;
    }

}
