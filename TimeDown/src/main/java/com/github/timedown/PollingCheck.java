package com.github.timedown;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.atomic.AtomicInteger;

/***
 *   created by android on 2019/5/6
 */
public class PollingCheck {
    public interface CheckCallback {
        boolean onCheck(int checkCount);
        void onComplete();
    }
    private static final int msg_what = 6060;
    private Handler handler;
    private long intervalTime=1000;
    private CheckCallback checkCallback;
    private AtomicInteger atomicInteger=new AtomicInteger();
    public PollingCheck() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg_what != msg.what || checkCallback == null) {
                    return;
                }
                boolean isSuccess = checkCallback.onCheck(atomicInteger.incrementAndGet());
                if(isSuccess){
                    checkCallback.onComplete();
                }else{
                    if (handler != null) {
                        handler.sendMessageDelayed(getMessage(), intervalTime);
                    }
                }
            }
        };
    }
    public static PollingCheck get(){
        return new PollingCheck();
    }
    public void start(CheckCallback checkCallback) {
        startPolling(0,500, checkCallback);
    }
    public void startForSecond(long intervalTimeSecond, CheckCallback checkCallback) {
        startPolling(0,intervalTimeSecond*1000,checkCallback);
    }
    public void startForSecond(long delayTimeSecond, long intervalTimeSecond, CheckCallback checkCallback) {
        startPolling(delayTimeSecond*1000,intervalTimeSecond*1000, checkCallback);
    }
    public void startForMillis( long intervalTimeMillis, CheckCallback checkCallback) {
        startPolling(0,intervalTimeMillis, checkCallback);
    }
    public void startForMillis(long delayTimeMillis,long intervalTimeMillis, CheckCallback checkCallback) {
        startPolling(delayTimeMillis,intervalTimeMillis, checkCallback);
    }
    private void startPolling(long delayTimeMillis, long intervalTimeMillis, CheckCallback checkCallback){
        reset();
        if(delayTimeMillis<0){
            delayTimeMillis=0;
        }
        if(intervalTimeMillis<0){
            intervalTimeMillis=0;
        }
        this.intervalTime=intervalTimeMillis;
        this.checkCallback = checkCallback;
        if(handler!=null){
            handler.sendMessageDelayed(getMessage(), delayTimeMillis);
        }
    }

    private Message getMessage() {
        Message obtain = Message.obtain();
        obtain.what = msg_what;
        return obtain;
    }


    public void reset(){
        this.checkCallback=null;
        if(atomicInteger==null){
            atomicInteger=new AtomicInteger();
        }
        atomicInteger.set(0);
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }
    public void onDestroy() {
        this.checkCallback=null;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    public void onDestroyAndClearHandler() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler=null;
        }
    }
    public static void onDestroy(PollingCheck pollingCheck) {
        if (pollingCheck != null) {
            pollingCheck.onDestroy();
        }
    }
    public static void onDestroyAndClearHandler(PollingCheck pollingCheck) {
        if (pollingCheck != null) {
            pollingCheck.onDestroyAndClearHandler();
        }
    }
}
