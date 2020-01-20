# TimeDownProject
倒计时和轮询器
#### 倒计时
```java
 protected void onDestroy() {
     super.onDestroy();
     //一定要在act结束之前调用这个方法停止倒计时，否则倒计时继续，但是更改view时，view为空
     TimeCountDown.onDestroy(timeCountDown);
 }
```
```java
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
```
#### 轮询器
```java
protected void onDestroy() {
    super.onDestroy();
    //一定要在act结束之前调用这个方法停止倒计时，否则轮询继续，如果更改view时，view为空
    PollingCheck.onDestroy(pollingCheck);
}
```
```java
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