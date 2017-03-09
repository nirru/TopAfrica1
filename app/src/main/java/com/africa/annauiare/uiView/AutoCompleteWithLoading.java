package com.africa.annauiare.uiView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

public class AutoCompleteWithLoading extends AutoCompleteTextView {
    private ProgressBar mLoadingIndicator;


    public AutoCompleteWithLoading(Context context) {
        super(context);
    }

    public AutoCompleteWithLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteWithLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoCompleteWithLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public AutoCompleteWithLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, popupTheme);
    }


    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    public void dispayIndicator(){
        if(mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        this.setText("");
    }

    public void removeIndicator(){
        if(mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.GONE);
        }
    }
}