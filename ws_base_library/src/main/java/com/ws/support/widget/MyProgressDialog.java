package com.ws.support.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import com.ws.base.R;

import java.util.Objects;

/**
 * 描述信息
 *
 * @author ws
 * @date 2020/6/5 14:01
 * 修改人：ws
 */
public class MyProgressDialog extends Dialog {
    private static MyProgressDialog dialog;

    private MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static MyProgressDialog createProgrssDialog(Context context) {
        dialog = new MyProgressDialog(context, R.style.base_CommonDialog);
        dialog.setContentView(R.layout.dialog_progress);
        //ContentLoadingProgressBar progressBar = dialog.findViewById(R.id.progress);
        //progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        Objects.requireNonNull(dialog.getWindow()).getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }

    public MyProgressDialog setMessage(String msg) {
        TextView loadingTextView = dialog.findViewById(R.id.tv_status);
        if (!TextUtils.isEmpty(msg)) {
            loadingTextView.setText(msg);
            loadingTextView.setVisibility(View.VISIBLE);
        }
        else
            loadingTextView.setVisibility(View.GONE);
        return dialog;
    }

    public static void setWidth(Dialog dialog, Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (dm.widthPixels * 0.7);
        //params.height = (int) (dm.widthPixels * 0.9);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
