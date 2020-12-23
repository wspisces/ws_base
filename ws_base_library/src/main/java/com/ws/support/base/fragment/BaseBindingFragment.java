package com.ws.support.base.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ws.support.utils.ToastUtils;
import com.ws.support.widget.MyProgressDialogFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/23/20 2:30 PM
 * 修改人：ws
 */
public abstract class BaseBindingFragment<DB extends ViewDataBinding> extends Fragment
{
    public final static int     REQUEST_CODE_CHOOSE = 1001;
    public static String TAG = "";
    protected           Context mContext;
    private MyProgressDialogFragment mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        DB db = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initView(db);
        return db.getRoot();
    }

    /**
     * 获取资源ID
     *
     * @return 布局资源ID
     */
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     *
     * @param bindView 界面绑定对象
     */
    protected abstract void initView(DB bindView);

    protected abstract String tag();


    /**
     * 初始化Fragment。可通过参数savedInstanceState获取之前保存的值。
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        TAG = getTag();
    }


    /**
     * 公用组件：进度条
     */
    protected void initProgress(String msg)
    {
        try
        {
            if (null == mDialog)
            {
                mDialog = MyProgressDialogFragment.newInstance(msg);
                mDialog.setCancelable(true);
            }
            mDialog.setMessage(msg);
            mDialog.show(getFragmentManager());
        } catch (Exception ignored)
        {
        }

    }

    /**
     * 公用组件：关闭进度条
     */
    protected void hideProgress()
    {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing())
        {
            activity.runOnUiThread(() ->
            {
                if (null != mDialog
                        && mDialog.getDialog() != null
                        && mDialog.getDialog().isShowing())
                    mDialog.dismiss();
            });
        }

    }

    /**
     * 公用组件： Toast
     */
    public void showToast(String message)
    {
        if (TextUtils.isEmpty(message))
        {
            return;
        }
        ToastUtils.normal(message);
    }

    //跳转
    protected void gotoActivity(Class<?> cls, Bundle bundle)
    {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void gotoActivity(Class<?> cls)
    {
        startActivity(new Intent(getActivity(), cls));
    }

    protected void jumptoActivity(Class<?> cls, Bundle bundle)
    {
        gotoActivity(cls, bundle);
        getActivity().finish();
    }

    protected void jumptoActivity(Class<?> cls)
    {
        gotoActivity(cls);
        getActivity().finish();
    }

    @SuppressLint("CheckResult")
    protected void delayEnable(View view, long delay)
    {
        Observable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> view.setEnabled(true));
    }
}
