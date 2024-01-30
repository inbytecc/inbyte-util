package com.inbyte.commons.model.dict;

/**
 * 渠道类型
 *
 * @author chenjw
 * @date 2022/10/19
 */
public enum AppTypeDict {

    Weixin_MiniProgram(0, "微信小程序"),
    Alipay_MiniProgram(1, "支付宝小程序"),
    Douyin_MiniProgram(2, "抖音小程序"),
    Little_Red_Book_MiniProgram(3, "小红书小程序"),
    Android(10, "安卓App"),
    IOS(11, "IOS App"),
    H5(20, "H5"),
    ;

    public final int code;
    public final String name;

    AppTypeDict(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
