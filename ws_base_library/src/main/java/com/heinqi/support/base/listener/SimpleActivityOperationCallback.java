package com.heinqi.support.base.listener;

import android.view.View;

public class SimpleActivityOperationCallback implements IActivityOperationCallback {

    @Override
    public void hideKeyboard(View view) {

    }

    @Override
    public void showKeyboard(View view) {

    }

    @Override
    public boolean hideCoverView() {
        return false;
    }

    @Override
    public void showCoverView() {

    }

    @Override
    public boolean hideRequestView() {
        return false;
    }

    @Override
    public void showRequestView() {

    }

    @Override
    public void showRequestView(String prompt) {

    }

    @Override
    public void hideNetWorkErrorView() {

    }

    @Override
    public void showNetWorkErrorView(Runnable action) {

    }

    @Override
    public void showCoverContentView() {

    }

    @Override
    public boolean hideCoverContentView() {
        return false;
    }

    @Override
    public void initCoverContentView(View view) {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void exitApp() {

    }
}
