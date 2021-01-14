package com.ws.component.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.ws.base.R;


/**
 * dialog
 *
 * @author ws
 * @date 2020/6/5 14:01
 * 修改人：ws
 */
public class MyProgressDialog extends AppCompatDialog
{
    private static MyProgressDialog dialog;

    private MyProgressDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public static MyProgressDialog createProblemDialog(Context context, String message)
    {
        dialog = new MyProgressDialog(context, R.style.base_CommonDialog);
        dialog.setContentView(R.layout.dialog_progress);
        TextView loadingTextView = dialog.findViewById(R.id.tv_status);
        if (!TextUtils.isEmpty(message))
        {
            loadingTextView.setText(message);
            loadingTextView.setVisibility(View.VISIBLE);
        } else
            loadingTextView.setVisibility(View.GONE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void setWidth(AppCompatDialog dialog, Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (dm.widthPixels * 0.6);
        //params.height = (int) (dm.widthPixels * 0.9);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public MyProgressDialog setMessage(String msg)
    {
        TextView loadingTextView = dialog.findViewById(R.id.tv_status);
        if (!TextUtils.isEmpty(msg))
        {
            loadingTextView.setText(msg);
            loadingTextView.setVisibility(View.VISIBLE);
        } else
            loadingTextView.setVisibility(View.GONE);
        return dialog;
    }
}
