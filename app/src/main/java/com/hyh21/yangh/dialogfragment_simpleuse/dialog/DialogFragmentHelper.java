package com.hyh21.yangh.dialogfragment_simpleuse.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.hyh21.yangh.dialogfragment_simpleuse.R;
import com.hyh21.yangh.mylib.CommonDialogFragment;
import com.hyh21.yangh.mylib.IDialogResultListener;

import java.util.Calendar;


/**
 * Dialog提示框助手
 * <p>根据项目需要，自行添加方法来统一整个应用的Dialog风格</p>
 *
 * <p>简单调用，处于界面和dialogFragment之间，使用的时候只需要关注与AlertDialog的交互，
 * Helper会帮助你用DialogFragment来进行显示，
 * </p>
 *
 * <p>与AlertDialog不同需要注意的地方
 * 1、CancelListener和DissmissListener不能够设置了
 * 2、增加了一个数据返回监听的使用，对于输入框，自定义view的数据监听和返回可以使用
 * </p>
 * Created by yanghuang on 2016/5/3.
 */
public class DialogFragmentHelper {

    private static final String dialog_positive = "确定";
    private static final String dialog_negative = "取消";

    private static final String TAG_HEAD = DialogFragmentHelper.class.getName();

    /**
     * 加载中的弹出窗
     */
    private static final int PROGRESS_THEME = R.style.Base_AlertDialog;
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


    /**
     * 简单提示弹出窗
     */
    private static final int TIPS_THEME = R.style.Base_AlertDialog;
    private static final String TIPS_TAG = TAG_HEAD + ":tips";

    public static void showTips(FragmentManager manager, String mes) {
        showTips(manager, mes, true, null);
    }

    public static void showTips(FragmentManager manager, String mes, boolean cancelable) {
        showTips(manager, mes, cancelable, null);
    }

    public static void showTips(FragmentManager manager, final String mes, boolean cancelable, CommonDialogFragment.OnDialogCancelListener cListener) {

        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, TIPS_THEME);
                builder.setMessage(mes);
                builder.setNegativeButton(dialog_positive, null);
                return builder.create();
            }
        }, cancelable, cListener);
        dialog.show(manager, TIPS_TAG);
    }

    /**
     * 确定取消框
     */
    private static final int CONFIRM_THEME = R.style.Base_AlertDialog;
    private static final String CONFIRM_TAG = TAG_HEAD + ":confirm";

    public static void showConfirmDailog(FragmentManager manager, String msg, IDialogResultListener<Integer> dListener) {
        showConfirmDialog(manager, msg, dListener, true, null);
    }

    public static void showConfirmDialog(FragmentManager manager, final String msg, final IDialogResultListener<Integer> dListener, boolean cancelable, CommonDialogFragment.OnDialogCancelListener cListener) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, CONFIRM_THEME);
                builder.setMessage(msg);
                builder.setPositiveButton(dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dListener != null) {
                            dListener.onDataResult(which);
                        }
                    }
                });
                builder.setNegativeButton(dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dListener != null) {
                            dListener.onDataResult(which);
                        }
                    }
                });
                return builder.create();
            }
        }, cancelable, cListener);
        dialog.show(manager, CONFIRM_TAG);
    }

    /**
     * 带输入框的弹出窗
     */
    private static final int LIST_THEME = R.style.Base_AlertDialog;
    private static final String LIST_TAG = TAG_HEAD + ":list";

    public static void showListDialog(FragmentManager manager, final String title, final String[] items, final IDialogResultListener<Integer> dListener, boolean cancelable) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context, LIST_THEME);
                builder.setTitle(title);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dListener != null) {
                            dListener.onDataResult(which);
                        }
                    }
                });
                return builder.create();
            }
        }, cancelable, null);
        dialog.show(manager, LIST_TAG);
    }

    /**
     * 选择日期
     */
    private static final int DATE_THEME = R.style.Base_AlertDialog;
    private static final String DATE_TAG = TAG_HEAD + ":list";

    public static void showDataDialog(FragmentManager manager, final String title, final Calendar calendar, final IDialogResultListener<Calendar> dListener, final boolean cancelable) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                final DatePickerDialog dateDialog = new DatePickerDialog(context, DATE_THEME, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);

                        dListener.onDataResult(calendar);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dateDialog.setTitle(title);
                dateDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(dialog_positive);
                        dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(dialog_negative);
                    }
                });
                return dateDialog;
            }
        }, cancelable, null);
        dialog.show(manager, DATE_TAG);
    }

    /**
     * 选择时间
     */
    private static final int TIME_THEME = R.style.Base_AlertDialog;
    private static final String TIME_TAG = TAG_HEAD + ":time";

    public static void showTimeDialog(FragmentManager manager, final String title, final Calendar calendar, final IDialogResultListener<Calendar> dListener, final boolean cancelable) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                final TimePickerDialog dateDialog = new TimePickerDialog(context, TIME_THEME, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(dListener != null) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            dListener.onDataResult(calendar);
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                dateDialog.setTitle(title);
                dateDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        dateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(dialog_positive);
                        dateDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(dialog_negative);
                    }
                });
                return dateDialog;
            }
        }, cancelable, null);
        dialog.show(manager, DATE_TAG);
    }

    /**
     * 带输入框的弹出窗
     */
    private static final int INSERT_THEME = R.style.Base_AlertDialog;
    private static final String INSERT_TAG = TAG_HEAD + ":insert";

    public static void showInsertDialog(FragmentManager manager, final String title, final IDialogResultListener<String> dListener, boolean cancelable) {
        CommonDialogFragment dialog = CommonDialogFragment.newInstance(new CommonDialogFragment.OnCallDialog() {
            @Override
            public Dialog getDialog(final Context context) {
                final EditText et = new EditText(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context, INSERT_THEME);
                builder.setTitle(title);
                builder.setView(et);
                builder.setPositiveButton(dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dListener != null) {
                            dListener.onDataResult(et.getText().toString());
                        }
                    }
                });
                return builder.create();
            }
        }, cancelable, null);
        dialog.show(manager, INSERT_TAG);
    }

    /**
     * 改变单个button颜色的代码
     */
    /*final AlertDialog ad = builder.create();
    ad.setOnShowListener(new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            ad.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#123456"));
        }
    });
    return ad;*/

}
