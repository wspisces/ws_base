package com.ws.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
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

/**
 * 表单组件-行头带图标
 *
 * @author ws
 * 2020/8/13 15:31
 * 修改人：ws
 */
public class FormRowWithImage extends ConstraintLayout {
    private Button                 btnAction;
    private ImageView              ivUser;
    private ImageView              ivLeading;
    private TextView               tvTitle;
    private EditText            etContent;
    private OnFormClickListener listener;

    public FormRowWithImage(Context context) {
        super(context);
    }

    public FormRowWithImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FormRowWithImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ImageView getUserImage() {
        return ivUser;
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
        View mView = inflater.inflate(R.layout.form_row_image, this, true);
        ivUser = mView.findViewById(R.id.iv_user);
        ivLeading = mView.findViewById(R.id.iv_leading);
        ImageView ivArrow = mView.findViewById(R.id.iv_arrow);
        tvTitle = mView.findViewById(R.id.tv_title);
        etContent = mView.findViewById(R.id.et_content);
        btnAction = mView.findViewById(R.id.btn_action);
        btnAction.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick();
            }
        });
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormRowWithImage);
        tvTitle.setText(a.getString(R.styleable.FormRowWithImage_title));
        String hint = a.getString(R.styleable.FormRowWithImage_hint);
        int leadingImage = a.getResourceId(R.styleable.FormRowWithImage_leading, 0);
        ivLeading.setImageResource(leadingImage);
        etContent.setHint(hint);
        int type = a.getInt(R.styleable.FormRowWithImage_style, 0);
        ivUser.setVisibility(GONE);
        if (type == 0) {//默认输入
            int max = a.getInt(R.styleable.FormRowWithImage_maxlengh, -1); //最大输入字符
            boolean isPwd = a.getBoolean(R.styleable.FormRowWithImage_pwd, false);
            ivArrow.setVisibility(INVISIBLE);
            etContent.setEnabled(true);
            if (max > 0)
                etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
            if (isPwd) {
                //etContent.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else if (type == 1) {//头像选择
            ivUser.setVisibility(VISIBLE);
            etContent.setEnabled(false);
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
