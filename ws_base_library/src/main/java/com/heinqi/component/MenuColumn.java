package com.heinqi.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.heinqi.base.R;


/**
 * 主页菜单组件
 *
 * @author ws
 * 2020/6/19 15:31
 * 修改人：ws
 */
public class MenuColumn extends ConstraintLayout {

    OnMenuClickListener  listener;
    View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onClick();
            }
        }
    };
    private Context   mContext;
    private View      mView;
    private Button    mBtn;
    private ImageView iv;
    private TextView  tv;

    public MenuColumn(Context context) {
        super(context);
    }

    public MenuColumn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MenuColumn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setListener(OnMenuClickListener listener) {
        this.listener = listener;
    }

    public ImageView getIv() {
        return iv;
    }

    public TextView getTv() {
        return tv;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.menu_column, this, true);
        iv = mView.findViewById(R.id.iv_menu);
        tv = mView.findViewById(R.id.tv_menu);
        mBtn = mView.findViewById(R.id.btn_menu);
        mBtn.setOnClickListener(onClickListener);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuColumn);
        tv.setText(a.getString(R.styleable.MenuColumn_title));
        int image = a.getResourceId(R.styleable.MenuColumn_icon, R.mipmap.save_icon);
        iv.setImageResource(image);
        a.recycle();
    }

    public void setEnable(boolean enable) {
        if (enable) {
            mBtn.setOnClickListener(onClickListener);
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_333));
            Drawable tipsArrow = iv.getDrawable();
            tipsArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.text_color_333), PorterDuff.Mode.SRC_ATOP);
            iv.setImageDrawable(tipsArrow);
        } else {
            mBtn.setOnClickListener(null);
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_999));
            Drawable tipsArrow = iv.getDrawable();
            tipsArrow.setColorFilter(ContextCompat.getColor(mContext, R.color.text_color_999), PorterDuff.Mode.SRC_ATOP);
            iv.setImageDrawable(tipsArrow);
        }
    }

    public void setContent(int imgResouce, String menu) {
        iv.setImageResource(imgResouce);
        tv.setText(menu);
    }
}
