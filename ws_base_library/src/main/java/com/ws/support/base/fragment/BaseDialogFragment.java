package com.ws.support.base.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.ws.base.R;
import com.ws.support.base.activity.BaseActivity;
import com.ws.support.utils.KeyBoardUtils;
import com.ws.support.views.RefreshDialogView;


/**
 * 基础dialogfragment
 *
 * @author DWord.Wu
 * @time 2017/10/9 16:42
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private RefreshDialogView mRefreshView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, showSoft() ? R.style.CommonEditDialog : R.style.base_CommonDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layout = getContentLayout();
        if (layout == 0) {
            throw new IllegalArgumentException("需要设置布局");
        }
        View root = inflater.inflate(R.layout.dialog_base_fragment, container, false);
        mRefreshView = root.findViewById(R.id.base_view_rdv);
        FrameLayout contentContainer = root.findViewById(R.id.contentContainer);
        inflater.inflate(getContentLayout(), contentContainer);
        return root;
    }

    public void hideRequestView() {
        if (mRefreshView != null) {
            mRefreshView.hideDialog();
        }
    }

    public void showRequestView() {
        if (mRefreshView != null) {
            mRefreshView.showDialog();
        }
    }

    public void showRequestView(String prompt) {
        if (mRefreshView != null) {
            mRefreshView.showDialog(prompt);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (enableFullScreen()) {
            getDialog().getWindow().setLayout(dm.widthPixels, dm.heightPixels);
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (getWidthFloat() != 0f) {
            lp.width = (int) (dm.widthPixels * getWidthFloat());
        }
        if (getHeightFloat() != 0f) {
            lp.height = (int) (dm.heightPixels * getHeightFloat());
            lp.gravity = Gravity.BOTTOM;
        }
        if (isBottom()) {
            lp.gravity = Gravity.BOTTOM;
        }
        window.setAttributes(lp);
        if (!enableCancelable()) {
            setCancelable(false);
        }
    }

    /**
     * dialog从底部弹出
     *
     * @return
     */
    protected boolean isBottom() {
        return false;
    }

    @Override
    public void dismiss() {
        View view = getDialog().getCurrentFocus();
        if (view instanceof TextView) {
            KeyBoardUtils.hideKeyboard(view);
        }
        super.dismiss();
    }

    /**
     * 是否自动弹出软键盘
     *
     * @return
     */
    protected boolean showSoft() {
        return false;
    }

    protected float getWidthFloat() {
        return 0f;
    }

    protected float getHeightFloat() {
        return 0f;
    }

    /**
     * 是否可以返回
     *
     * @return
     */
    protected boolean enableCancelable() {
        return true;
    }

    /**
     * 是否启用全屏
     *
     * @return
     */
    protected boolean enableFullScreen() {
        return false;
    }

    protected abstract void initData();

    protected abstract int getContentLayout();

    /**
     * 网络请求结束后，执行操作
     */
    protected void doFinishRequest() {
    }

    protected void showToast(String msg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showToast(msg);
        }
    }
}
