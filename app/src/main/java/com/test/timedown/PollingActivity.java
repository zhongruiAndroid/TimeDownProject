package com.test.timedown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.timedown.PollingCheck;

public class PollingActivity extends AppCompatActivity {

    private TextView tvNum;
    private PollingCheck pollingCheck;
    private boolean isFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plling);

        tvNum = findViewById(R.id.tvNum);

        Button btStart=findViewById(R.id.btStart);
        btStart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                tvNum.setText("轮询次数：0次");
                isFind=false;
                if(pollingCheck==null){
                    pollingCheck = PollingCheck.get();
                }
                //一秒轮询一次
                pollingCheck.startForSecond(1, new PollingCheck.CheckCallback() {
                    @Override
                    public boolean onCheck(int checkCount) {
                        /*如果满足要求，提前return少执行一次*/
                        if(isFind){
                            return true;
                        }
                        tvNum.setText("轮询次数："+checkCount+"次");

                        //一旦返回true,代表轮询结束，接着调用complete方法
                        return false;
                    }

                    /*或者这样写也行,延后return，多执行一次*/
                   /* public boolean onCheck(int checkCount) {
                        tvNum.setText("轮询次数："+checkCount+"次");
                        return isFind;
                    }*/
                    @Override
                    public void onComplete() {
                        //轮询完成
                        tvNum.setText(tvNum.getText()+"(完成)");
                    }
                });
            }
        });

        Button btEnd=findViewById(R.id.btEnd);
        btEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFind=true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //一定要在act结束之前调用这个方法停止倒计时，否则轮询继续，如果更改view时，view为空
        PollingCheck.onDestroy(pollingCheck);
    }
}
