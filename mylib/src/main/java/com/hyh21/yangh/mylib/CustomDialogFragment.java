package com.hyh21.yangh.mylib;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 可以自定义弹出窗的dialogFragment
 * dialogFragment的简单封装，依赖外部传入自定义view来构建。
 *
 * Created by yanghuang on 2016/5/5.
 */
public class CustomDialogFragment extends DialogFragment {

    /**
     * 监听弹出窗是否被取消
     */
    private OnDialogCancelListener mCancelListener;
    /**
     * 回调获得需要显示的dialog
     */
    private OnCallView mOnCallView;

    public interface OnDialogCancelListener {
        void onCancel();
    }

    public interface OnCallView {
        View getCustomView(Context context);
    }

    public static CustomDialogFragment newInstance(OnCallView call, boolean cancelable) {
        return newInstance(call, cancelable, null);
    }

    public static CustomDialogFragment newInstance(OnCallView call, boolean cancelable, OnDialogCancelListener cancelListener) {
        CustomDialogFragment instance = new CustomDialogFragment();
        instance.setCancelable(cancelable);
        instance.mCancelListener = cancelListener;
        instance.mOnCallView = call;
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(null == mOnCallView) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        //启用窗体的扩展特性。
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return mOnCallView.getCustomView(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置背景为半透明，很鬼畜，保留一下
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel();
        }
    }
}
