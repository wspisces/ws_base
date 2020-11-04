package com.ws.component.dialog;

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

import com.ws.base.R;
import com.ws.support.utils.ScreenUtils;
import com.ws.support.utils.StringUtils;
import com.ws.support.utils.ToastUtils;


/**
 * 输入弹窗
 *
 * @author ws
 * 2020/6/29 17:57
 * 修改人：ws
 */
public class InputDialogFragment extends DialogFragment
{
    TextView tvTitle;
    Button   btnCancel, btnOk;
    EditText                      et;
    String                        title          = "";
    String                        errorMsg       = "";
    String                        hint           = "";
    String                        defaultContent = "";
    OnInputDialogFragmentListener listener;
    boolean                       cancelable     = true;

    private InputDialogFragment()
    {
    }

    public static InputDialogFragment newInstance(String title, String defaultContent, String hint, String error, @NonNull OnInputDialogFragmentListener listener)
    {
        InputDialogFragment dialog = new InputDialogFragment();
        dialog.title = title;
        dialog.hint = hint;
        dialog.errorMsg = error;
        dialog.defaultContent = defaultContent;
        dialog.listener = listener;
        return dialog;
    }

    public static InputDialogFragment newInstance(String title, String defaultContent, String hint, String error, boolean cancelable, @NonNull OnInputDialogFragmentListener listener)
    {
        InputDialogFragment dialog = new InputDialogFragment();
        dialog.title = title;
        dialog.hint = hint;
        dialog.errorMsg = error;
        dialog.defaultContent = defaultContent;
        dialog.cancelable = cancelable;
        dialog.listener = listener;
        return dialog;
    }

    public static InputDialogFragment newInstance(String title, String hint, String error, boolean cancelable, @NonNull OnInputDialogFragmentListener listener)
    {
        InputDialogFragment dialog = new InputDialogFragment();
        dialog.title = title;
        dialog.hint = hint;
        dialog.errorMsg = error;
        dialog.defaultContent = "";
        dialog.cancelable = cancelable;
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_Fullscreen_Bottom);
        //dialog.setCanceledOnTouchOutside(true);
        //dialog.setCancelable(true);
        //设置actionbar的隐藏
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_input, container, false);
        tvTitle = view.findViewById(R.id.tv_title);
        et = view.findViewById(R.id.et);
        et.setHint(hint);
        et.setText(defaultContent);
        if (defaultContent.length() > 0)
        {
            et.setSelection(defaultContent.length());
        }
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
        getDialog().setCancelable(cancelable);
        getDialog().setCanceledOnTouchOutside(cancelable);
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

    public void show(@NonNull FragmentManager manager)
    {
        show(manager, "InputDialogFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        btnCancel.setOnClickListener(view ->
        {
            listener.onCancel(this);
        });
        btnOk.setOnClickListener(view ->
        {
            if (et.getText().length() > 0)
            {
                listener.onCommit(this, et.getText().toString());
            } else
            {
                if (StringUtils.isNotEmptyWithNull(errorMsg))
                    ToastUtils.error(errorMsg);
            }
        });
    }

    public interface OnInputDialogFragmentListener
    {
        void onCommit(DialogFragment dialogFragment, String str);

        void onCancel(DialogFragment dialogFragment);
    }
}
