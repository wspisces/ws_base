package com.ws.support.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;

import com.ws.base.R;
import com.ws.support.widget.MyProgressDialog;

import java.util.Objects;

/**
 * Created by curry on 2018/6/21.
 */
public class ProgressHandler extends Handler {
    public static final int SHOW_PROGRESS = 0;
    public static final int DISMISS_PROGRESS = 1;
//    public static DialogInterface.OnKeyListener onKeyListener = (dialog, keyCode, event) -> {
////        if (keyCode == KeyEvent.KEYCODE_BACK
////                && event.getAction() == KeyEvent.ACTION_DOWN) {
////            dismissDialog();
////        }
//        return false;
//    };
    private MyProgressDialog pd;
    private Context mContext;
    private ProgressCancelListener mProgressCancelListener;
    private boolean cancelable;

    public ProgressHandler(Context context, boolean cancelable) {
        this.mContext = context;
        this.cancelable = cancelable;
    }

    public ProgressHandler(Context context, ProgressCancelListener listener, boolean cancelable) {
        this.mContext = context;
        mProgressCancelListener = listener;
        this.cancelable = cancelable;
    }

    public void initProgressDialog(String message) {
        if (pd == null) {
            pd = MyProgressDialog.createProgrssDialog(mContext);
            pd.setCancelable(cancelable);
            pd.setMessage(message);
            pd.setCanceledOnTouchOutside(false);
            //pd.setOnKeyListener(onKeyListener);
            Objects.requireNonNull(pd.getWindow()).setGravity(Gravity.CENTER);
            if (cancelable) {
                pd.setOnCancelListener(dialog -> {
                    if (mProgressCancelListener != null) {
                        mProgressCancelListener.onProgressCanceled();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();//显示进度条
            }
        }

    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();//取消进度条
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS:
                initProgressDialog(msg.getData().getString("message"));
                break;
            case DISMISS_PROGRESS:
                dismissProgressDialog();
                break;
        }
    }

    public void showProgressDialog() {
        Bundle bundle = new Bundle();
        bundle.putString("message", mContext.getString(R.string.tip_loading));
        Message message = obtainMessage(ProgressHandler.SHOW_PROGRESS);
        message.setData(bundle);
        message.sendToTarget();
    }

    public void disMissProgressDialog() {
        obtainMessage(ProgressHandler.DISMISS_PROGRESS).sendToTarget();
    }

    //接口，用来取消进度条
    public interface ProgressCancelListener {
        void onProgressCanceled();
    }
}
