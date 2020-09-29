package com.ws.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ws.base.R;

/**
 * 表单组件-纵向排布
 *
 * @author ws
 * 2020/6/19 15:31
 * 修改人：ws
 */
public class FormColum extends ConstraintLayout {
    private Button              btnAction;
    private TextView            tvTitle;
    private EditText            etContent;
    private OnFormClickListener listener;

    public FormColum(Context context) {
        super(context);
    }

    public FormColum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FormColum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public EditText getEt() {
        return etContent;
    }

    public void setListener(OnFormClickListener listener) {
        this.listener = listener;
        btnAction.setVisibility(VISIBLE);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.form_colum, this, true);
        ImageView ivArrow = mView.findViewById(R.id.iv_arrow);
        tvTitle = mView.findViewById(R.id.tv_title);
        etContent = mView.findViewById(R.id.et_content);
        btnAction = mView.findViewById(R.id.btn_action);
        btnAction.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick();
            }
        });
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormColum);
        tvTitle.setText(a.getString(R.styleable.FormColum_title));
        String hint = a.getString(R.styleable.FormColum_hint);
        String content = a.getString(R.styleable.FormColum_content);
        etContent.setHint(hint);
        etContent.setText(content);
        int type = a.getInt(R.styleable.FormColum_style, 0);
        btnAction.setVisibility(GONE);
        if (type == 0) {//默认输入
            int max = a.getInt(R.styleable.FormColum_maxlengh, -1); //最大输入字符
            //boolean isPwd = a.getBoolean(R.styleable.FormRow_pwd, false);
            ivArrow.setVisibility(INVISIBLE);
            etContent.setEnabled(true);
            if (max > 0)
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
            //if (isPwd) {
            //    etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
            //}
        } else if (type == 2) {//弹窗
            etContent.setEnabled(false);
        } else if (type == 3) {//信息展示
            etContent.setEnabled(false);
            ivArrow.setVisibility(INVISIBLE);
        }
        a.recycle();

    }

    public String getContent() {
        return etContent.getText().toString();
    }

    //设置内容
    public void setContent(String content) {
        etContent.setText(content);
    }

    //提示文字
    public void setHintText(String hint) {
        etContent.setHint(hint);
    }
}
