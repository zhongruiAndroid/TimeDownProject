package com.test.timedown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.timedown.TimeCountDown;

public class TimeDownActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btTime1;
    private Button btTime2;
    private Button btTime3;
    private Button btTime4;
    private Button btTime5;
    private TimeCountDown timeCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_down);

        btTime1 = findViewById(R.id.btTime1);
        btTime1.setOnClickListener(this);

        btTime2 = findViewById(R.id.btTime2);
        btTime2.setOnClickListener(this);

        btTime3 = findViewById(R.id.btTime3);
        btTime3.setOnClickListener(this);

        btTime4 = findViewById(R.id.btTime4);
        btTime4.setOnClickListener(this);

        btTime5 = findViewById(R.id.btTime5);
        btTime5.setOnClickListener(this);
    }

    private String string;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btTime1:
                string = getString(R.string._1);
                btTime1.setText(string);
//                开始倒计时(6秒)(马上执行)(每1秒倒计时一次)
                startTimeDownForSecond(btTime1, string, 6, 0, 1);
                break;
            case R.id.btTime2:
                string = getString(R.string._2);
                btTime2.setText(string);
//                开始倒计时(6秒)(延迟2秒执行)(每1秒倒计时一次)
                startTimeDownForSecond(btTime2, string, 6, 2, 1);
                break;
            case R.id.btTime3:
                string = getString(R.string._3);
                btTime3.setText(string);
//                开始倒计时(6秒)(延迟2秒执行)(每2秒倒计时一次)
                startTimeDownForSecond(btTime3,string, 6, 2, 2);
                break;
            case R.id.btTime4:
                string = getString(R.string._4);
                btTime4.setText(string);
//                开始倒计时(6000毫秒)(马上执行)(每500毫秒倒计时一次)
                startTimeDownForMillis(btTime4,string, 6000, 0, 500);
                break;
            case R.id.btTime5:
                string = getString(R.string._5);
                btTime5.setText(string);
//                (6000毫秒)(延迟2000毫秒执行)(每500毫秒倒计时一次)
                startTimeDownForMillis(btTime5,string, 6000, 2000, 500);
                break;

        }
    }
    private Button preBt;
    private String preStr;
    /*毫秒*/
    public void startTimeDownForMillis(final Button btTime,final String changText, long totalTime, long delayTimeMillis, long intervalTimeMillis) {
        if(preBt!=null){
            preBt.setText(preStr);
        }
        if(timeCountDown==null){
            //启动之前判空，否则重复实例化会存在多个倒计时
            timeCountDown = TimeCountDown.get();
        }
        timeCountDown.startForMillis(totalTime,delayTimeMillis,intervalTimeMillis, new TimeCountDown.TimeCallback() {
            @Override
            public void onNext(long time) {
                btTime.setText(changText + "(" + time + "s)");
            }
            @Override
            public void onComplete() {
                btTime.setText(changText + "(完成)");
            }
        });
        preBt=btTime;
        preStr=changText;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要在act结束之前调用这个方法停止倒计时，否则倒计时继续，但是更改view时，view为空
        TimeCountDown.onDestroy(timeCountDown);
    }
    /*秒*/
    public void startTimeDownForSecond(final Button btTime, final String changText, long totalTime, long delayTimeMillis, long intervalTimeMillis) {
        if(timeCountDown!=null){
            //如果多次重复点击,清除之前的倒计时
            timeCountDown.reset();
        }
        timeCountDown = TimeCountDown.get();
        timeCountDown.startForSecond(totalTime,delayTimeMillis,intervalTimeMillis, new TimeCountDown.TimeCallback() {
            @Override
            public void onNext(long time) {
                btTime.setText(changText + "(" + time + "s)");
            }
            @Override
            public void onComplete() {
                btTime.setText(changText + "(完成)");
            }
        });
    }

}
