package com.heinqi.support.base.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.heinqi.base.R;
import com.heinqi.support.base.entity.CnSimpleIndexInfoImp;


/**
  * SimpleIndexAdapter.class
  * 基础标记适配器
  * @author Johnny.xu
  * time:2018/12/3
  */
public class SimpleIndexAdapter extends CnBaseAdapter<CnSimpleIndexInfoImp, SimpleIndexAdapter.SimpleIndexViewHolder> {

    public SimpleIndexAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.adapter_base_m_simple_index_item;
    }

    @Override
    public SimpleIndexViewHolder getViewHolder(View convertView) {
        return new SimpleIndexViewHolder(convertView);
    }

    @Override
    public void initData(int position, SimpleIndexViewHolder vh) {
        CnSimpleIndexInfoImp infoImp = getItem(position);
        vh.tv_index.setText(String.valueOf(position + 1));
        vh.tv_content.setText(infoImp.getSimpleContent());
    }

    class SimpleIndexViewHolder {

        TextView tv_index;
        TextView tv_content;

        SimpleIndexViewHolder(View view) {
            tv_index = view.findViewById(R.id.tv_index);
            tv_content = view.findViewById(R.id.tv_content);
        }
    }
}


