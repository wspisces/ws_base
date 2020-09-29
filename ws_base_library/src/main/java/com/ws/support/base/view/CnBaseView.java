package com.ws.support.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ws.support.base.listener.ViewOperationListener;


/**
  * CnBaseView.class
  * 基础底层控件
  * time:2017/7/17
  */
public abstract class CnBaseView extends View implements ViewOperationListener {

    protected Context mContext;
    protected int mMaxHeight, mMaxWidth;

    public CnBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initParams(attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMaxHeight = MeasureSpec.getSize(heightMeasureSpec);
        changeDefaultSize();
        super.setMeasuredDimension(mMaxWidth, mMaxHeight);
    }

    protected void changeDefaultSize() {

    }

    protected int getResColorById(int id) {
        return mContext.getResources().getColor(id);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void drawRect(Canvas canvas, float left, float top, float right, float bottom, float strokeWidth, int strokeColor, int solidColor, Paint paint) {

        paint.setStrokeWidth(strokeWidth);
        float insideLeft = left + strokeWidth/2;
        float insideTop = top + strokeWidth/2;
        float insideRight = right - strokeWidth/2;
        float insideBottom = bottom - strokeWidth/2;

        boolean isNoInside = insideLeft >= insideRight || insideBottom <= insideTop;

        float fillLeft = left + strokeWidth;
        float fillTop = top + strokeWidth;
        float fillRight = right - strokeWidth;
        float fillBottom = bottom - strokeWidth;
        boolean isHasFill = fillLeft <= fillRight && fillTop <= fillBottom;

        Paint.Style style = paint.getStyle();
        switch (style) {
            case FILL: {
                paint.setColor(solidColor);
                canvas.drawRect(left, top, right, bottom, paint);
            }
            break;

            case STROKE: {
                if (isNoInside) {
                    paint.setColor(strokeColor);
                    canvas.drawRect(left, top, right, bottom, paint);
                } else {
                    paint.setColor(strokeColor);
                    canvas.drawRect(insideLeft, insideTop, insideRight, insideBottom, paint);
                }
            }
            break;

            case FILL_AND_STROKE: {
                if (isNoInside) {
                    paint.setColor(strokeColor);
                    canvas.drawRect(left, top, right, bottom, paint);
                } else {
//                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(strokeColor);
                    canvas.drawRect(insideLeft, insideTop, insideRight, insideBottom, paint);

                    if (isHasFill) {
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(solidColor);
                        canvas.drawRect(fillLeft, fillTop, fillRight, fillBottom, paint);
                    }
                }
            }
            break;
        }
//        paint.setColor(defaultColor);
    }
}
