package com.heinqi.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.heinqi.base.R;


/**
 * 侧滑菜单组件
 *
 * @author ws
 * 2020/6/19 15:31
 * 修改人：ws
 */
public class MenuRow extends ConstraintLayout {

    private ImageView iv;

    public ImageView getIv() {
        return iv;
    }

    public TextView getTv() {
        return tv;
    }

    private TextView tv;
    public MenuRow(Context context) {
        super(context);
    }

    public MenuRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public MenuRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = inflater.inflate(R.layout.menu_row, this, true);
        iv = mView.findViewById(R.id.iv_menu);
        tv = mView.findViewById(R.id.tv_menu);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MenuRow);
        tv.setText(a.getString(R.styleable.MenuRow_title));
        int image = a.getResourceId(R.styleable.MenuRow_icon, R.mipmap.save_icon);
        iv.setImageResource(image);
        a.recycle();
    }

    public void setContent(int imgResouce,String menu){
        iv.setImageResource(imgResouce);
        tv.setText(menu);
    }
}
