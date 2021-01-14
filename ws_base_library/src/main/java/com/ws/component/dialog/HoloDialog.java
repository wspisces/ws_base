package com.ws.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ws.base.R;
import com.ws.component.ClearEditText;
import com.ws.component.InputFilterFloatMinMax;
import com.ws.support.utils.MyToastUtil;
import com.ws.support.utils.ScreenUtils;
import com.ws.support.utils.SharePreferUtil;
import com.ws.support.utils.StringUtils;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/21/20 7:11 PM
 * 修改人：ws
 */
public class HoloDialog
{
    private final Context context;
    OnHoloDialogClickListener  listener;
    OnHoloDialogClickListener2 listener2;
    private Dialog   dialog;
    private TextView tvTitle, tvMessage, tvCancel, tvOk;
    private ClearEditText etName, etAmount;

    public HoloDialog(Context context)
    {
        this.context = context;
    }

    public HoloDialog builder()
    {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_holo, null);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.HoloDialogStyle);
        dialog.setContentView(view);


        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOk = view.findViewById(R.id.tv_ok);
        tvCancel.setOnClickListener(view1 ->
        {
            if (listener != null)
                listener.onCancelClick();
            dialog.dismiss();
        });
        tvOk.setOnClickListener(view12 ->
        {
            if (listener != null)
                listener.onOKClick(dialog);
        });

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (SharePreferUtil.getScreenOrientation())
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 3 * 2;
        } else
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 3;
        }
        //lp.height = ScreenUtils.getScreenHeight(context) / 2;
        dialogWindow.setAttributes(lp);

        return this;
    }


    public HoloDialog builderCreateCategory()
    {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_holo_create_category, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOk = view.findViewById(R.id.tv_ok);
        etName = view.findViewById(R.id.et_name);
        etAmount = view.findViewById(R.id.et_amount);
        etAmount.setFilters(new InputFilter[]{new InputFilterFloatMinMax((float) 0.01, 9999)});

        tvCancel.setOnClickListener(view1 ->
        {
            if (listener2 != null)
                listener2.onCancelClick();
            dialog.dismiss();
        });
        tvOk.setOnClickListener(view12 ->
        {
            if (etName.getText().length() == 0)
            {
                MyToastUtil.show("请输入菜名");
                return;
            }

            if (etAmount.getText().length() == 0)
            {
                MyToastUtil.show("请输入金额");
                return;
            }
            if (listener2 != null)
                listener2.onOKClick(dialog, etName.getText().toString(), etAmount.getText().toString());
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.HoloDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (SharePreferUtil.getScreenOrientation())
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 4 * 3;
        } else
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 2;
        }
        dialogWindow.setAttributes(lp);

        return this;
    }

 /*   public HoloDialog builderInputAmount()
    {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_holo_create_category, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOk = view.findViewById(R.id.tv_ok);
        etName = view.findViewById(R.id.et_name);
        etAmount = view.findViewById(R.id.et_amount);
        etAmount.setFilters(new InputFilter[]{new InputFilterFloatMinMax((float) 0.01, 9999)});

        tvCancel.setOnClickListener(view1 ->
        {
            if (listener2 != null)
                listener2.onCancelClick();
            dialog.dismiss();
        });
        tvOk.setOnClickListener(view12 ->
        {
            if (etName.getText().length() == 0)
            {
                MyToastUtil.show("请输入菜名");
                return;
            }

            if (etAmount.getText().length() == 0)
            {
                MyToastUtil.show("请输入金额");
                return;
            }
            if (listener2 != null)
                listener2.onOKClick(dialog, etName.getText().toString(), etAmount.getText().toString());
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.HoloDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (SharePreferUtil.getScreenOrientation())
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 2;
        } else
        {
            lp.width = ScreenUtils.getScreenWidth(context) / 2;
        }
        dialogWindow.setAttributes(lp);

        return this;
    }*/


    public HoloDialog setCategoryInfo(String name, String price)
    {
        if (StringUtils.isNotEmptyWithNull(name))
        {
            etName.setText(name);
            etName.setSelection(name.length());
        }
        if (StringUtils.isNotEmptyWithNull(price))
        {
            etAmount.setText(price);
        }
        return this;
    }

    public HoloDialog setCancelable(boolean cancel)
    {
        dialog.setCancelable(cancel);
        return this;
    }

    public HoloDialog setCanceledOnTouchOutside(boolean cancel)
    {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public HoloDialog setTitle(String title)
    {
        if (StringUtils.isNotEmptyWithNull(title))
        {
            tvTitle.setText(title);
        }
        return this;
    }

    public HoloDialog setMessage(String message)
    {
        if (StringUtils.isNotEmptyWithNull(message))
        {
            tvMessage.setText(message);
        }
        return this;
    }

    public HoloDialog setOneButton(String btnStr)
    {
        if (StringUtils.isNotEmptyWithNull(btnStr))
        {
            tvOk.setText(btnStr);
        }
        tvCancel.setVisibility(View.GONE);
        return this;
    }

    public HoloDialog setOneButton()
    {
        tvCancel.setVisibility(View.GONE);
        return this;
    }

    public HoloDialog setOnClickListener(OnHoloDialogClickListener l)
    {
        listener = l;
        return this;
    }

    public HoloDialog setOnClickListener2(OnHoloDialogClickListener2 l)
    {
        listener2 = l;
        return this;
    }

    public void show()
    {
        // setSheetItems();
        dialog.show();
    }

    /**
     * edittext只能输入数值的时候做最大最小的限制
     */
    private void setRegion(final EditText edit, final float MIN_MARK, final float MAX_MARK)
    {
        edit.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (start > 1)
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK)
                        {
                            s = String.valueOf(MAX_MARK);
                            edit.setText(s);
                        } else if (num < MIN_MARK)
                        {
                            s = String.valueOf(MIN_MARK);
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s != null && !s.equals(""))
                {
                    if (MIN_MARK != -1 && MAX_MARK != -1)
                    {
                        double markVal = 0;
                        try
                        {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK)
                        {
                            edit.setText(String.valueOf(MAX_MARK));
                            edit.setSelection(String.valueOf(MAX_MARK).length());
                        }
                        return;
                    }
                }
            }
        });
    }

    public interface OnHoloDialogClickListener
    {
        void onOKClick(Dialog dialog);

        void onCancelClick();
    }

    public interface OnHoloDialogClickListener2
    {
        void onOKClick(Dialog dialog, String name, String amount);

        void onCancelClick();
    }
}


