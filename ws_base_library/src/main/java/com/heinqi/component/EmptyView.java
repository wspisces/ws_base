package com.heinqi.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.heinqi.base.R;

/**
 * 空布局界面
 *
 * @author ws
 * 2020/9/4 16:08
 * 修改人：ws
 */
public class EmptyView extends ConstraintLayout {
    OnClickListener listener;
    private Button    btn;
    private ImageView iv;
    private TextView  tv;

    public EmptyView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.empty_view, this, true);
        iv = v.findViewById(R.id.empty_iv);
        tv = v.findViewById(R.id.empty_tv);
        btn = v.findViewById(R.id.empty_btn);
        btn.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick();
            }
        });
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
        btn.setVisibility(VISIBLE);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.empty_view, this, true);
        iv = v.findViewById(R.id.empty_iv);
        tv = v.findViewById(R.id.empty_tv);
        btn = v.findViewById(R.id.empty_btn);
        btn.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClick();
            }
        });
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        String msg = a.getString(R.styleable.EmptyView_msg);
        if (TextUtils.isEmpty(msg)) {
            tv.setVisibility(GONE);
        } else {
            tv.setText(msg);
        }
        int image = a.getResourceId(R.styleable.MenuRow_icon, R.mipmap.no_data);
        iv.setImageResource(image);
        a.recycle();
    }

    public void setMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            tv.setVisibility(GONE);
        } else {
            tv.setText(msg);
            tv.setVisibility(VISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    public void setImage(@IdRes int resource) {
        iv.setImageResource(resource);
    }


    public interface OnClickListener {
        void onClick();
    }
}
