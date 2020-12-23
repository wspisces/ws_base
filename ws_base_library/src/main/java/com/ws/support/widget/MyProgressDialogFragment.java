package com.ws.support.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.ws.base.R;

import java.util.Objects;

/**
 * 等待窗口
 *
 * @author ws
 * @date 2020/6/2 14:12
 * 修改人：ws
 */
public class MyProgressDialogFragment extends DialogFragment {
    ContentLoadingProgressBar progressBar;
    TextView tv;
    String msg;
    private DialogInterface.OnDismissListener mOnClickListener;

    public static MyProgressDialogFragment newInstance(String msg) {
        MyProgressDialogFragment dialog = new MyProgressDialogFragment();
        dialog.msg = msg;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress_fragment, container, false);
        //progressBar = view.findViewById(R.id.progress);
        //progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(),R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        //Sprite doubleBounce = new DoubleBounce();
        //progressBar.setIndeterminateDrawable(doubleBounce);

        tv = view.findViewById(R.id.tv_status);
        tv.setText(msg);
        if (msg.isEmpty()){
            tv.setVisibility(View.GONE);
        }else{
            tv.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Dialog dialog = super.onCreateDialog(savedInstanceState);
        //设置actionbar的隐藏
        Dialog dialog = new Dialog(getContext(), R.style.theme_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
            Window window = getDialog().getWindow();
            assert window != null;
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
            //设置宽度
            DisplayMetrics dm = new DisplayMetrics();
            Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
            Objects.requireNonNull(dialog.getWindow()).setLayout((int) (dm.widthPixels * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.mOnClickListener = listener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnClickListener != null) {
            mOnClickListener.onDismiss(dialog);
        }
    }

    public void setMessage(String msg) {
        this.msg = msg;
        if (tv != null) {
            tv.setText(msg);
            if (msg.isEmpty()){
                tv.setVisibility(View.GONE);
            }else{
                tv.setVisibility(View.VISIBLE);
            }
        }
    }

    public void show(FragmentManager manager){
        super.show(manager,MyProgressDialogFragment.class.getSimpleName());
    }
}
