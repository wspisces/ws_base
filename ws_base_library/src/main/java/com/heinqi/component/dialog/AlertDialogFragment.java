package com.heinqi.component.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.heinqi.base.R;
import com.heinqi.support.utils.ScreenUtils;
import com.heinqi.support.utils.StringUtils;
import com.heinqi.support.utils.ToastUtils;


/**
 * 消息弹窗
 *
 * @author ws
 * 2020/8/17 19:14
 * 修改人：ws
 */
public class AlertDialogFragment extends DialogFragment {
    TextView tvTitle;
    Button   btnCancel, btnOk;
    String                        title = "";
    OnAlertDialogFragmentListener listener;

    private AlertDialogFragment() {
    }

    public static AlertDialogFragment newInstance(String title, @NonNull OnAlertDialogFragmentListener listener) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.title = title;
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_Fullscreen_Bottom);
        dialog.setCanceledOnTouchOutside(true);
        //设置actionbar的隐藏
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_alert, container, false);
        tvTitle = view.findViewById(R.id.tv_title);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnOk = view.findViewById(R.id.btn_ok);
        tvTitle.setText(title);
        // 隐藏标题栏, 不加弹窗上方会一个透明的标题栏占着空间
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 必须设置这两个,才能设置宽度
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        // 遮罩层透明度
        getDialog().getWindow().setDimAmount(0.8f);

        Window window = getDialog().getWindow();
        //window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getScreenWidth(getActivity()) * 0.8f);
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.dimAmount = 0.7f;
        attributes.gravity = Gravity.CENTER;
        getDialog().getWindow().setAttributes(attributes);
        return view;
    }

    public void show(@NonNull FragmentManager manager) {
        show(manager, "InputDialogFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnCancel.setOnClickListener(view -> dismiss());
        btnOk.setOnClickListener(view -> {
            listener.onCommit(this);
        });
    }

    public interface OnAlertDialogFragmentListener {
        void onCommit(DialogFragment dialogFragment);
    }
}
