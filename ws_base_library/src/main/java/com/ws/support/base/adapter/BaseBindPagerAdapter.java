package com.ws.support.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/23/20 2:39 PM
 * 修改人：ws
 */
public abstract class BaseBindPagerAdapter<DB extends ViewDataBinding, K, V> extends PagerAdapter
{
    private LinkedHashMap<K, V>    map;
    private LinkedHashMap<K, View> views;
    private Map<Integer, K>        temp;

    public BaseBindPagerAdapter(LinkedHashMap<K, V> map) {
        this.map = map;
        if(map == null) {
            map = new LinkedHashMap<>(0);
        }
        views = new LinkedHashMap<>(map.size());
        temp = new LinkedHashMap<>(map.size());
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        int position = 0;
        while (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();
            temp.put(position++, next.getKey());
        }
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        DB binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), getResourceId(), container, false);
        BindViewHolder holder = new BindViewHolder();
        holder.bindView = binding;
        //initView(holder, temp.get(position));
        views.put(temp.get(position), binding.getRoot());
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(temp.get(position)));
    }

    protected class BindViewHolder {
        public DB bindView;
    }

    /**
     * 获取资源ID
     *
     * @return 布局资源ID
     */
    public abstract int getResourceId();

    /**
     * 初始化界面
     * @param holder 界面句柄
     * @param key 子项对应的键
     * @param value 子项实例
     * */
   // public abstract void initView(BindViewHolder holder, K key, V value);
}


