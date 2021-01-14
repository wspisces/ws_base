package com.ws.component.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
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
public class HoloTimeDialog
{
    private final Context context;
    OnHoloDialogClickListener listener;
    private Dialog   dialog;
    private TextView tvTitle, tvMessage, tvCancel, tvOk;
    private TimePicker timePicker;

    public HoloTimeDialog(Context context)
    {
        this.context = context;
    }


    @SuppressLint("NewApi")
    public HoloTimeDialog builder()
    {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_holo_time, null);
        tvTitle = view.findViewById(R.id.tv_title);
        tvMessage = view.findViewById(R.id.tv_message);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvOk = view.findViewById(R.id.tv_ok);
        timePicker = view.findViewById(R.id.picker);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);  //设置点击事件不弹键盘
        timePicker.setIs24HourView(true);   //设置时间显示为24小时
        tvCancel.setOnClickListener(view1 ->
        {
            if (listener != null)
                listener.onCancelClick();
            dialog.dismiss();
        });
        tvOk.setOnClickListener(view12 ->
        {
            if (listener != null)
                listener.onOKClick(dialog, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.HoloDialogStyle);
        dialog.setContentView(view);
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

        dialogWindow.setAttributes(lp);

        return this;
    }

    @SuppressLint("NewApi")
    public HoloTimeDialog setTime(int hour, int min)
    {
        if (hour == -1 || min == -1) return this;
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        return this;
    }

    @SuppressLint("NewApi")
    public HoloTimeDialog setMaxTime(int hour, int min)
    {
        if (hour == -1 || min == -1) return this;
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");
        NumberPicker hourNumberPicker = (NumberPicker) timePicker.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) timePicker.findViewById(minuteNumberPickerId);
        hourNumberPicker.setMaxValue(hour);
        minuteNumberPicker.setMaxValue(min);
        return this;
    }

    @SuppressLint("NewApi")
    public HoloTimeDialog setMinTime(int hour, int min)
    {
        if (hour == -1 || min == -1) return this;
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");
        NumberPicker hourNumberPicker = (NumberPicker) timePicker.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) timePicker.findViewById(minuteNumberPickerId);
        hourNumberPicker.setMinValue(hour);
        minuteNumberPicker.setMinValue(min);
        return this;
    }

    public HoloTimeDialog setCancelable(boolean cancel)
    {
        dialog.setCancelable(cancel);
        return this;
    }

    public HoloTimeDialog setCanceledOnTouchOutside(boolean cancel)
    {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public HoloTimeDialog setTitle(String title)
    {
        if (StringUtils.isNotEmptyWithNull(title))
        {
            tvTitle.setText(title);
        }
        return this;
    }

   /* public HoloTimeDialog setMessage(String message)
    {
        if (StringUtils.isNotEmptyWithNull(message))
        {
            tvMessage.setText(message);
        }
        return this;
    }*/

    public HoloTimeDialog setOneButton(String btnStr)
    {
        if (StringUtils.isNotEmptyWithNull(btnStr))
        {
            tvOk.setText(btnStr);
        }
        tvCancel.setVisibility(View.GONE);
        return this;
    }

    public HoloTimeDialog setOneButton()
    {
        tvCancel.setVisibility(View.GONE);
        return this;
    }

    public HoloTimeDialog setOnClickListener(OnHoloDialogClickListener l)
    {
        listener = l;
        return this;
    }


    public void show()
    {

        dialog.show();
    }


    public interface OnHoloDialogClickListener
    {
        void onOKClick(Dialog dialog, int hour, int min);

        void onCancelClick();
    }

}


