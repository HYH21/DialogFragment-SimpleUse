package com.hyh21.yangh.dialogfragment_simpleuse.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hyh21.yangh.dialogfragment_simpleuse.R;
import com.hyh21.yangh.dialogfragment_simpleuse.dialog.DialogFragmentHelper;
import com.hyh21.yangh.dialogfragment_simpleuse.util.TimeUtil;
import com.hyh21.yangh.mylib.IDialogResultListener;

import java.util.Calendar;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button mBtnProgress;
    private Button mBtnWarning;
    private Button mBtnSimpleSelect;
    private Button mBtnSelectDate;
    private Button mBtnSelectTime;
    private Button mBtnMroe;
    private Button mBtnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnProgress = (Button) findViewById(R.id.btn_progress);
        mBtnWarning = (Button) findViewById(R.id.btn_tips);
        mBtnSimpleSelect = (Button) findViewById(R.id.btn_confirm);
        mBtnSelectDate = (Button) findViewById(R.id.btn_select_date);
        mBtnSelectTime = (Button) findViewById(R.id.btn_select_time);
        mBtnMroe = (Button) findViewById(R.id.btn_list);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);

        mBtnProgress.setOnClickListener(this);
        mBtnWarning.setOnClickListener(this);
        mBtnSimpleSelect.setOnClickListener(this);
        mBtnSelectDate.setOnClickListener(this);
        mBtnSelectTime.setOnClickListener(this);
        mBtnMroe.setOnClickListener(this);
        mBtnInsert.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_progress:
                DialogFragmentHelper.showProgress(getFragmentManager(), "正在加载中...");
                break;

            case R.id.btn_tips:
                DialogFragmentHelper.showTips(getFragmentManager(), "提示框");
                break;

            case R.id.btn_confirm:
                DialogFragmentHelper.showConfirmDailog(getFragmentManager(), "确定框", new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        switch (result) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;

            case R.id.btn_select_date:
                DialogFragmentHelper.showDataDialog(getFragmentManager(), TimeUtil.getCurrentYearMonth(), TimeUtil.getTime(), new IDialogResultListener<Calendar>() {
                    @Override
                    public void onDataResult(Calendar result) {
                        Toast.makeText(MainActivity.this, TimeUtil.calendarToString(result, TimeUtil.YEAR_MONTH_DAY), Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;

            case R.id.btn_select_time:
                DialogFragmentHelper.showTimeDialog(getFragmentManager(), "选择时间", TimeUtil.getTime(), new IDialogResultListener<Calendar>() {
                    @Override
                    public void onDataResult(Calendar result) {
                        Toast.makeText(MainActivity.this, TimeUtil.calendarToString(result, "HH:mm"), Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;

            case R.id.btn_insert:
                DialogFragmentHelper.showInsertDialog(getFragmentManager(), "输入框", new IDialogResultListener<String>() {
                    @Override
                    public void onDataResult(String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;

            case R.id.btn_list:
                DialogFragmentHelper.showListDialog(getFragmentManager(), "列表", new String[]{"1", "2", "3"}, new IDialogResultListener<Integer>() {
                    @Override
                    public void onDataResult(Integer result) {
                        Toast.makeText(MainActivity.this, "按钮：" + result, Toast.LENGTH_SHORT).show();
                    }
                }, true);
                break;

            default:
                break;
        }
    }
}
