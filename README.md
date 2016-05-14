# DialogFragment-SimpleUse
- 统一风格，简单调用，沿袭AlertDialog使用习惯
- ![](https://github.com/HYH21/DialogFragment-SimpleUse/blob/master/app/imagery/20160514150045.png)
![](https://github.com/HYH21/DialogFragment-SimpleUse/blob/master/app/imagery/20160514150101.png)

# 简单DialogFragment封装

## AlertDialog和DialogFragment使用分析
**AlertDialog的使用习惯**
- 系统自带的样式
```java
new AlertDialog.Builder(LeaveActivity.this).setTitle("选择请假类型")
        .setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                    int which) {
                // TODO Auto-generated method stub
                reason.setText(items[which]);
            }
        }).show();
```
- setView的方式添加控件
```java
edit.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edit.setEnabled(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(
                LeaveActivity.this)
                .setTitle("请输入用户密码")
                .setView(edit)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                password = edit.getText().toString();
                                Verification.start();
                            }
        }).setNegativeButton("取消", null);

                builder.show();
```
从这两种使用方法来看，每个dialog都给逻辑代码带来大量的代码，并会产生很多层次的缩进，给代码的阅读带来了一定的难度。
因为一个APP中的弹出窗种类是不多的，样式也是需要统一，所以可以用一个类来统一创建这些弹出窗，逻辑代码只需要调用，并拿到自己想要的信息即可以。

再来看一下**DialogFrament的使用**
DialogFragment的使用主要是继承重写（自定义布局:`onCreateView()`,直接返回Dialog:`onCreateDialog）`，单独使用newInstance一个方法来负责生成实类，调用其show()方法即可以显示弹出窗。
- **DialogFragment注意事项**
`onCreateDialog()`里面返回的dialog，不能设置其`OnDismissListenter`和`OnCancelListener`，然而DialogFragment里面也没有提供这两个动作的监听，所以需要我们自己实现

**其他**
屏幕选装，AlertDialog会消失，DialogFragment虽然可以重新find出来，但是数据已经丢失，监听器也会失效。假如需求需要选装屏幕的话，可以给Activity设置`android:configChanges="orientation|screenSize"`

## 封装思路
封装目的是为了使用DialogFragment来替代AlertDialog，减少出错。为了保持AlertDialog的使用习惯并实现简单调用。
- 结构图
![](https://github.com/HYH21/DialogFragment-SimpleUse/blob/master/app/imagery/20160514150116.png)
- **简化调用**，一句话调用
- **简化回调**，所有按钮监听和输入框的监听在Helper里面实现，并统一使用**IDialogResultListener**来给Activity回调信息（这个结果将使用泛型）
- **屏蔽DialogFragment的包装**，尽可能保持AlertDialog的原使用习惯

## 代码实现
### CommonDialogFragment
**处理缺少的OnCancelListener**
```java
public interface OnDialogCancelListener {
    void onCancel();
}

@Override
    public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);
    if (mCancelListener != null) {
        mCancelListener.onCancel();
    }
}
```
**维持一个接口，在OnCreateDialog中回调Helper获得需要创建的Dialog**
```java
public interface OnCallDialog {
    Dialog getDialog(Context context);
}

@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (null == mOnCallDialog) {
        super.onCreateDialog(savedInstanceState);
    }
    return mOnCallDialog.getDialog(getActivity());
}
```
**newInstance来创建这个实例**
```java
public static CommonDialogFragment newInstance(OnCallDialog call, boolean cancelable,     OnDialogCancelListener cancelListener) {
    CommonDialogFragment instance = new CommonDialogFragment();
    instance.setCancelable(cancelable);
    instance.mCancelListener = cancelListener;
    instance.mOnCallDialog = call;
    return instance;
}
```
### DialogFragmentHelper
调用newInstance获得实例，实现OnCallDialog接口提供给DialogFragment来获得Dialog，根据需要实现其中的cancelListener。
```java
    /**
     * 加载中的弹出窗
     */
    private static final int PROGRESS_THEME = R.style.YXY_Base_AlertDialog;
    private static final String PROGRESS_TAG = TAG_HEAD + ":progress";

    public static CommonDialogFragment showProgress(FragmentManager manager, String mes) {
        return showProgress(manager, mes, true, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager manager, String mes, boolean cancelable) {
        return showProgress(manager, mes, cancelable, null);
    }

    public static CommonDialogFragment showProgress(FragmentManager manager, final String mes, boolean cancelable, CommonDialogFragment.OnDialogCancelListener cListener) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                ProgressDialog pd = new ProgressDialog(context, PROGRESS_THEME);
                pd.setMessage(mes);
                return pd;
            }
        }, cancelable, cListener);
        dialog.show(manager, PROGRESS_TAG);
        return dialog;
    }
```
**不需要监听按钮或者其他的操作的可以不设置监听**
**添加按钮和数据返回的监听，以EditText为例子**
```java
public static void showInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> dListener, boolean cancelable) {
    //方法中添加IDialogResultListener<String> dListener
    //并指定数据返回的类型
    ...
    builder.setPositiveButton(dialog_positive, new DialogInterface.OnClickListener() {
        @Override
            public void onClick(DialogInterface dialog, int which) {
            //在确定按钮中直接将editText中获得的内容返回给接口！！！
                  dListener.onDataResult(et.getText().toString());
        }
    });
    ...
}
```

## 使用
### 在Helper中实现需要的Dialog，并调用show
```java
public static CommonDialogFragment showProgress(FragmentManager manager, String mes) {
    return showProgress(manager, mes, true, null);
}

public static CommonDialogFragment showProgress(FragmentManager manager, String mes, boolean cancelable) {
    return showProgress(manager, mes, cancelable, null);
}

public static CommonDialogFragment showProgress(FragmentManager manager, final String mes, boolean cancelable, CommonDialogFragment.OnDialogCancelListener cListener) {
    CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
        @Override
            public Dialog getDialog(final Context context) {
            ProgressDialog pd = new ProgressDialog(context, PROGRESS_THEME);
            pd.setMessage(mes);
            return pd;
        }
    }, cancelable, cListener);
    dialog.show(manager, PROGRESS_TAG);
    return dialog;
}
```

### 一句话调用
```java
mDialogFragment = DialogFragmentHelper.showProgress(getFragmentManager(), "正在修改...");

mDialogFragment.dismiss();
```

### 监听Dialog操作
```java
@Override
    public void onDataResult(String result) {
    if (result == null || "".equals(result)) {
        Toast.makeText(getActivity(), "名字不能为空", Toast.LENGTH_SHORT).show();
    } else {
        editAlbumName(editItem, result);
    }
}
```

















