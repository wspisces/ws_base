package com.ws.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ws.base.R;
import com.ws.support.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/21/20 7:11 PM
 * 修改人：ws
 */
public class ActionSheetDialog
{
    private final Context         context;
    private       Dialog          dialog;
    private       LinearLayout    lLayout_content;
    private       ScrollView      sLayout_content;
    private       List<SheetItem> sheetItemList;


    public ActionSheetDialog(Context context)
    {
        this.context = context;
    }

    public ActionSheetDialog builder()
    {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_actionsheet, null);
        // 设置Dialog最小宽度为屏幕宽度
        //view.setMinimumWidth(ScreenUtils.getScreenWidth(context));
        // 获取自定义Dialog布局中的控件
        sLayout_content = view.findViewById(R.id.sv);
        lLayout_content = view
                .findViewById(R.id.ll);
        Button txt_cancel = view.findViewById(R.id.btn_cancel);
        txt_cancel.setOnClickListener(v -> dialog.dismiss());

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = ScreenUtils.getScreenWidth(context);
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel)
    {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel)
    {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param strItem  条目名称
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem,
                                          OnSheetItemClickListener listener)
    {
        if (sheetItemList == null)
        {
            sheetItemList = new ArrayList<>();
        }
        sheetItemList.add(new SheetItem(strItem, listener));
        return this;
    }

    /**
     * 设置条目布局
     */
    private void setSheetItems()
    {
        if (sheetItemList == null || sheetItemList.size() <= 0)
        {
            return;
        }

        int size = sheetItemList.size();

        // 添加条目过多的时候控制高度
        if (size >= 7)
        {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sLayout_content
                    .getLayoutParams();
            params.height = ScreenUtils.getScreenHeight(context) / 2;
            sLayout_content.setLayoutParams(params);
        }

        // 循环添加条目
        for (int i = 1; i <= size; i++)
        {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i - 1);
            String strItem = sheetItem.name;
            //SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = sheetItem.itemClickListener;

            TextView textView = new TextView(context);
            textView.setText(strItem);
            textView.setTextSize(21);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.btn_transparent);
            textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_333));
            // 字体颜色
           /* if (color == null)
            {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else
            {
                textView.setTextColor(Color.parseColor(color.getName()));
            }*/

            // 高度
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (55 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height));

            // 点击事件
            textView.setOnClickListener(v ->
            {
                listener.onClick(index);
                dialog.dismiss();
            });

            lLayout_content.addView(textView);
        }
    }

    public void show()
    {
        setSheetItems();
        dialog.show();
    }

/*    public enum SheetItemColor
    {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        SheetItemColor(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }*/

    public interface OnSheetItemClickListener
    {
        void onClick(int which);
    }

    public class SheetItem
    {
        String                   name;
        OnSheetItemClickListener itemClickListener;
        //SheetItemColor           color;

        public SheetItem(String name,
                         OnSheetItemClickListener itemClickListener)
        {
            this.name = name;
            //this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }
}


