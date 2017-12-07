package com.pixelall.rxstudy.moshi;

/**
 * Created by lxl on 2017/8/21.
 * IODH 也是单例中处理并发的问题
 */

public class IODHDanLi {
    public static IODHDanLi getInstance(){
        return Holder.IODHDanLi;
    }
    private static class Holder{
        public static IODHDanLi IODHDanLi=new IODHDanLi();
    }
}
