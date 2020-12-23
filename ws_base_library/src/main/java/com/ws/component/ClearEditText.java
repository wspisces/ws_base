package com.ws.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


import androidx.appcompat.widget.AppCompatEditText;

import com.ws.base.R;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/18/20 1:14 PM
 * 修改人：ws
 */
public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher
{
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    private boolean  isHideDeleteBtn;
    /**
     * 控件是否有焦点
     */
    private boolean  hasFoucs;

    public ClearEditText(Context context)
    {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs)
    {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void init()
    {
        if (mClearDrawable == null)
        {
            mClearDrawable = getResources().getDrawable(R.drawable.ic_clear, null);
        }
        setLongClickable(false);
        setTextIsSelectable(false);
        setCustomSelectionActionModeCallback(new ActionMode.Callback()
        {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item)
            {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {

            }
        });

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setLongClickable(false);
        setTextIsSelectable(false);
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 由于不能直接给EditText设置点击事件，所以用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置在 EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
       if (event.getAction() == MotionEvent.ACTION_UP)
        {
            if (getCompoundDrawables()[2] != null)
            {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())))
                        && event.getY() > getPaddingRight()
                        && event.getY() < (getHeight() - getPaddingRight());
                if (touchable)
                {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        this.hasFoucs = hasFocus;
        if (hasFocus)
        {
            setClearIconVisible(getText().length() > 0);
        } else
        {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible)
    {
        if (isHideDeleteBtn)
        {
            return;
        }
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    public void setHideDeleteBtn(boolean isHide)
    {
        isHideDeleteBtn = isHide;
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after)
    {
        if (null == getText())
        {
            return;
        }
        if (hasFoucs)
        {
            setClearIconVisible(getText().length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    public boolean onTextContextMenuItem(int id)
    {
        return true;
    }
}

