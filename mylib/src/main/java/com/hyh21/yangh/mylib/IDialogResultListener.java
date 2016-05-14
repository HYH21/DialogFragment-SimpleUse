package com.hyh21.yangh.mylib;

/**
 * 用于DialogFragmentHelper与逻辑层之间进行数据监听
 *
 * Created by yanghuang on 2016/5/4.
 */
public interface IDialogResultListener<T> {
    void onDataResult(T result);
}
