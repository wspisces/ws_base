package com.ws.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ws.base.R;
import com.orhanobut.logger.Logger;

/**
 * 表单组件-横向排布
 *
 * @author ws
 * 2020/6/19 15:31
 * 修改人：ws
 */
public class FormRow extends ConstraintLayout
{
    int inputType = 0;
    private Button              btnAction;
    private ImageView           ivUser;
    private TextView            tvTitle;
    private EditText            etContent;
    private OnFormClickListener listener;

    public FormRow(Context context)
    {
        super(context);
    }

    public FormRow(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public FormRow(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TextView getTitle()
    {
        return tvTitle;
    }

    public ImageView getUserImage()
    {
        return ivUser;
    }

    public EditText getEt()
    {
        return etContent;
    }

    public void setListener(OnFormClickListener listener)
    {
        this.listener = listener;
        btnAction.setVisibility(VISIBLE);
    }

    private void init(Context context, AttributeSet attrs)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.form_row, this, true);
        ivUser = mView.findViewById(R.id.iv_user);
        ImageView ivArrow = mView.findViewById(R.id.iv_arrow);
        tvTitle = mView.findViewById(R.id.tv_title);
        etContent = mView.findViewById(R.id.et_content);
        btnAction = mView.findViewById(R.id.btn_action);
        btnAction.setOnClickListener(view ->
        {
            if (listener != null)
            {
                listener.onClick();
            }
        });
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormRow);
        tvTitle.setText(a.getString(R.styleable.FormRow_title));
        String hint = a.getString(R.styleable.FormRow_hint);

        etContent.setHint(hint);
        int type = a.getInt(R.styleable.FormRow_style, 0);

        ivUser.setVisibility(GONE);
        if (type == 0)
        {//默认输入
            int max = a.getInt(R.styleable.FormRow_maxlengh, -1); //最大输入字符
            boolean isPwd = a.getBoolean(R.styleable.FormRow_pwd, false);
            ivArrow.setVisibility(INVISIBLE);
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
            } else
                if (inputType == 2)
                {//手机号
                    etContent.setInputType(InputType.TYPE_CLASS_PHONE);
                } else
                    if (inputType == 3)
                    {//邮箱地址
                        etContent.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    }
        } else
            if (type == 1)
            {//头像选择
                ivUser.setVisibility(VISIBLE);
                etContent.setEnabled(false);
            } else
                if (type == 2)
                {//弹窗
                    etContent.setEnabled(false);
                } else
                    if (type == 3)
                    {//信息展示
                        etContent.setEnabled(false);
                        ivArrow.setVisibility(INVISIBLE);
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
