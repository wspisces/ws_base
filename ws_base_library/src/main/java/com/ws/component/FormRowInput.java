package com.ws.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.orhanobut.logger.Logger;
import com.ws.base.R;

/**
 * 表单组件-横向排布
 *
 * @author ws
 * 2020/6/19 15:31
 * 修改人：ws
 */
public class FormRowInput extends ConstraintLayout
{
    int inputType = 0;
    private TextView      tvTitle;
    private ClearEditText etContent;

    public FormRowInput(Context context)
    {
        super(context);
    }

    public FormRowInput(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public FormRowInput(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TextView getTitle()
    {
        return tvTitle;
    }

    public ClearEditText getEt()
    {
        return etContent;
    }


    private void init(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.form_row_input, this, true);
        tvTitle = mView.findViewById(R.id.tv_title);
        etContent = mView.findViewById(R.id.et_content);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormRowInput);
        tvTitle.setText(a.getString(R.styleable.FormRowInput_title));
        String hint = a.getString(R.styleable.FormRowInput_hint);
        etContent.setHint(hint);
        String content = a.getString(R.styleable.FormRowInput_content);
        etContent.setText(content);

        int imeType = a.getInt(R.styleable.FormRowInput_imeOptions, EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        //禁止横屏是全屏
        etContent.setImeOptions(imeType);

        int max = a.getInt(R.styleable.FormRow_maxlengh, -1); //最大输入字符
        boolean isPwd = a.getBoolean(R.styleable.FormRow_pwd, false);
        etContent.setEnabled(true);
        if (max > 0)
            etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
        if (isPwd)
        {
            //etContent.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
            etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        inputType = a.getInt(R.styleable.FormRow_input, 0);
        //默认0,问正常文字输入
        if (inputType == 1)
        {//整数数字
            etContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (inputType == 2)
        {//手机号
            etContent.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (inputType == 3)
        {//邮箱地址
            etContent.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }else if (inputType == 1000)
        {
            etContent.setEnabled(false);
        }


        a.recycle();

    }

    public String getContent()
    {
        return etContent.getText().toString();
    }

    //设置内容
    public void setContent(String content)
    {
        etContent.setText(content);
    }

    //提示文字
    public void setHintText(String hint)
    {
        etContent.setHint(hint);
    }

    //可输入的最大长度
    public void setMaxLength(int i)
    {
        if (i > 0)
            etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
    }

    //数字输入是限制输入的最大值
    public void setMax(int max)
    {
        if (inputType != 1)
        {
            Logger.e("输入类型不为数字,不能做最大值判断");
            return;
        }
        etContent.setFilters(new InputFilter[]{new InputFilterFloatMinMax(0, max)});
    }

    //数字输入是限制输入的范围
    public void setMax(int max, int min)
    {
        if (inputType != 1)
        {
            Logger.e("输入类型不为数字,不能做最大值判断");
            return;
        }
        etContent.setFilters(new InputFilter[]{new InputFilterFloatMinMax(min, max)});
    }
}
