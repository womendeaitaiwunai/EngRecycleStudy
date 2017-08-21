package com.pixelall.rxstudy.moshi;

/**
 * Created by lxl on 2017/8/21.
 * 双重检查机制 --处理并发问题
 */

public class TwoCheckDanLi {
    private volatile TwoCheckDanLi instance=null;

    public TwoCheckDanLi getInstance(){
        if (instance==null){
            synchronized (this){
                if (instance==null){
                    instance=new TwoCheckDanLi();
                }
            }
        }
        return instance;
    }

}
