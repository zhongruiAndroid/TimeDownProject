# TimeDownProject
倒计时和轮询器
#### 倒计时
```java
 protected void onDestroy() {
     super.onDestroy();
     //一定要在act结束之前调用这个方法停止倒计时，否则倒计时继续，如果更改view时view为空会引起空指针
     TimeCountDown.onDestroy(timeCountDown);
 }
```
```java
if(timeCountDown==null){
     //启动之前判空，否则重复实例化会存在多个倒计时
     timeCountDown = TimeCountDown.get();
}
//totalTime:需要倒计时的时间
//delayTimeMillis:延迟执行
//intervalTimeMillis:倒计时间隔
//startForMillis:毫秒
//startForSecond:秒
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
```
#### 轮询器
```java
protected void onDestroy() {
    super.onDestroy();
    //一定要在act结束之前调用这个方法停止倒计时，否则轮询继续，如果更改view时view为空会引起空指针
    PollingCheck.onDestroy(pollingCheck);
}
```
```java
if(pollingCheck==null){
    pollingCheck = PollingCheck.get();
}
//delayTimeMillis:延迟执行
//intervalTimeMillis:轮询间隔时间
//startForMillis:毫秒
//startForSecond:秒
pollingCheck.startForMillis( delayTimeMillis, intervalTimeMillis, new PollingCheck.CheckCallback() {
    @Override
    public boolean onCheck(int checkCount) {
        /*如果满足要求，提前return少执行一次*/
        if(isFind){
             //一旦返回true,代表轮询结束，下面的代码不会执行，接着调用complete方法
            return true;
        }
        tvNum.setText("轮询次数："+checkCount+"次");

        return false;
    }

    /*或者这样写也行,延后return，多执行一次*/
    /*public boolean onCheck(int checkCount) {
        tvNum.setText("轮询次数："+checkCount+"次");
        return isFind;
    }*/
    @Override
    public void onComplete() {
        //轮询完成
        tvNum.setText(tvNum.getText()+"(完成)");
    }
});
```


| [ ![Download](https://api.bintray.com/packages/zhongrui/mylibrary/TimeDown/images/download.svg) ](https://bintray.com/zhongrui/mylibrary/TimeDown/_latestVersion) | 最新版本号|
|--------|----|
```gradle
implementation 'com.github:TimeDown:版本号'
```
