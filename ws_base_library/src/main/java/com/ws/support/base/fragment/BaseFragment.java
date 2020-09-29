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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.ws.support.base.listener.IActivityOperationCallback;
import com.ws.support.utils.ToastUtils;
import com.ws.support.widget.MyProgressDialogFragment;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Fragment基础类
 *
 * @author Johnny.xu
 * date 2017/2/16
 */
public abstract class BaseFragment extends Fragment {
    public final static int REQUEST_CODE_CHOOSE = 1001;
    protected Context mContext;

    private MyProgressDialogFragment mDialog;

    private IActivityOperationCallback mIActivityOperationCallback;

    public static String TAG = "";

    protected abstract String tag();

    /**
     * 执行该方法时，Fragment与Activity已经完成绑定，该方法有一个Activity类型的参数，
     * 代表绑定的Activity，这时候你可以执行诸如mActivity = activity的操作。
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        if (activity instanceof IActivityOperationCallback) {
            mIActivityOperationCallback = (IActivityOperationCallback) activity;
        }
    }

    /**
     * 初始化Fragment。可通过参数savedInstanceState获取之前保存的值。
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getTag();
    }

    /**
     * 初始化Fragment的布局。加载布局和findViewById的操作通常在此函数内完成，
     * 但是不建议执行耗时的操作，比如读取数据库数据列表。
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，
     * 在该方法内可以进行与Activity交互的UI操作，所以在该方法之前Activity的onCreate方法并未执行完成，
     * 如果提前进行交互操作，会引发空指针异常。
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 执行该方法时，Fragment由不可见变为可见状态。
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 执行该方法时，Fragment处于活动状态，用户可与之交互。
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 保存当前Fragment的状态。该方法会自动保存Fragment的状态，比如EditText键入的文本，
     * 即使Fragment被回收又重新创建，一样能恢复EditText之前键入的文本。
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 执行该方法时，Fragment完全不可见。
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。
     * 通常在ViewPager+Fragment的方式下会调用此方法。
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 解除与Activity的绑定。在onDestroy方法之后调用。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 页面隐藏状态改变
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 公用组件：进度条
     */
    protected void initProgress(String msg) {
        try {
            if (null == mDialog) {
                mDialog = MyProgressDialogFragment.newInstance(msg);
                mDialog.setCancelable(true);
            }
            mDialog.setMessage(msg);
            mDialog.show(getFragmentManager());
        } catch (Exception ignored) {
        }

    }

    /**
     * 公用组件：关闭进度条
     */
    protected void hideProgress() {
        FragmentActivity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(() -> {
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
    public void showToast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        ToastUtils.normal(message);
        //EasyToastUtil.showToast(message);
    }
    //跳转
    protected void gotoActivity(Class<?> cls,Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle!= null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    protected void gotoActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    protected void jumptoActivity(Class<?> cls,Bundle bundle) {
        gotoActivity(cls,bundle);
        getActivity().finish();
    }

    protected void jumptoActivity(Class<?> cls) {
        gotoActivity(cls);
        getActivity().finish();
    }

    @SuppressLint("CheckResult")
    protected void delayEnable(View view, long delay) {
        Observable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> view.setEnabled(true));
        //new Handler().postDelayed(() -> view.setEnabled(true), delay);
    }
}
