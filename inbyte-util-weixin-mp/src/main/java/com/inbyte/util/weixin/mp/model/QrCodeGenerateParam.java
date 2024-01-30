package com.inbyte.util.weixin.mp.model;

import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import lombok.Getter;
import lombok.Setter;

/**
 * 二维码生成参数
 *
 * @author chenjw
 * @date 2023-7-25
 */
@Getter
@Setter
public class QrCodeGenerateParam {

    /**
     * scene	string	是	最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     */
    private String scene;
    /**
     * page	string	否	默认是主页，页面 page，例如 pages/index/index，根路径前不要填加 /，不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面。scancode_time为系统保留参数，不允许配置
     */
    private String page;
    /**
     * env_version	string	否	要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版。
     */
    private String envVersion;
    /**
     * width	number	否	默认430，二维码的宽度，单位 px，最小 280px，最大 1280px
     */
//    @Range(min = 430, max = 1280, message = "二维码宽带最小 430, 最大 1280 哦")
    private int width;
    /**
     * auto_color	bool	否	自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     */
    private Integer autoColor;
    /**
     * line_color	object	否	默认是{"r":0,"g":0,"b":0} 。auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     */
    private WxMaCodeLineColor lineColor;
    /**
     * is_hyaline	bool	否	默认是false，是否需要透明底色，为 true 时，生成透明底色的小程序
     */
    private Integer isHyaline;
}
