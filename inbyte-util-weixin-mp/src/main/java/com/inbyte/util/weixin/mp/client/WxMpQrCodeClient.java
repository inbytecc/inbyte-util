package com.inbyte.util.weixin.mp.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.util.weixin.mp.model.QrCodeGenerateParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

/**
 * 微信小程序用户
 *
 * @author chenjw
 */
@Slf4j
@Component
public class WxMpQrCodeClient {

    @Autowired
    private WxMaService wxMaService;

    /**
     * 接口B: 获取小程序码（永久有效、数量暂无限制）.
     * <pre>
     * 通过该接口生成的小程序码，永久有效，数量暂无限制。
     * 用户扫描该码进入小程序后，将统一打开首页，开发者需在对应页面根据获取的码中 scene 字段的值，再做处理逻辑。
     * 使用如下代码可以获取到二维码中的 scene 字段的值。
     * 调试阶段可以使用开发工具的条件编译自定义参数 scene=xxxx 进行模拟，开发工具模拟时的 scene 的参数值需要进行 urlencode
     * </pre>
     *
     *  其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * @param page       必须是已经发布的小程序页面，例如 "pages/index/index" ,如果不填写这个字段，默认跳主页面
     * param checkPath  默认true 检查 page 是否存在，为 true 时 page 必须是已经发布的小程序存在的页面（否则报错）；
     *  为 false 时允许小程序未发布或者 page 不存在，但 page 有数量上限（60000个）请勿滥用
     *  param envVersion 默认"release" 要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"
     *  param width      默认430 二维码的宽度
     *  param autoColor  默认true 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
     *  param lineColor  autoColor 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
     *  param isHyaline  是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
     * @return 文件内容字节数组
     * @throws WxErrorException 异常
     */
    @GetMapping
    public R<byte[]> qrCode(String appId, String scene, String page, int width) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        try {
            byte[] wxaCodeUnLimitBytes = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(
                    scene, page, true,"release",
                    width, true, null, true);
            return R.ok(wxaCodeUnLimitBytes);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.error(e.toString());
        }
    }


    /**
     * 接口B: 获取小程序码（永久有效、数量暂无限制）.
     * Base64格式
     *
     * <pre>
     * 通过该接口生成的小程序码，永久有效，数量暂无限制。
     * 用户扫描该码进入小程序后，将统一打开首页，开发者需在对应页面根据获取的码中 scene 字段的值，再做处理逻辑。
     * 使用如下代码可以获取到二维码中的 scene 字段的值。
     * 调试阶段可以使用开发工具的条件编译自定义参数 scene=xxxx 进行模拟，开发工具模拟时的 scene 的参数值需要进行 urlencode
     * </pre>
     *
     *  其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * @param page       必须是已经发布的小程序页面，例如 "pages/index/index" ,如果不填写这个字段，默认跳主页面
     * param checkPath  默认true 检查 page 是否存在，为 true 时 page 必须是已经发布的小程序存在的页面（否则报错）；
     *  为 false 时允许小程序未发布或者 page 不存在，但 page 有数量上限（60000个）请勿滥用
     *  param envVersion 默认"release" 要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"
     *  param width      默认430 二维码的宽度
     *  param autoColor  默认true 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
     *  param lineColor  autoColor 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
     *  param isHyaline  是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
     * @return 文件内容字节数组
     * @throws WxErrorException 异常
     */
    public R<String> qrCodeBase64(String appId, String scene,
                                  String page, int width) {
        if (!wxMaService.switchover(appId)) {
            log.error("未找到对应appId={}的配置，请核实！", appId);
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        try {
            byte[] wxaCodeUnLimitBytes = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(
                    scene, page, true,"release",
                    width, true, null, true);
            return R.ok("获取成功", Base64.getEncoder().encodeToString(wxaCodeUnLimitBytes));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.error(e.toString());
        }
    }


    /**
     * 接口B: 获取小程序码（永久有效、数量暂无限制）.
     * Base64格式
     *
     * <pre>
     * 通过该接口生成的小程序码，永久有效，数量暂无限制。
     * 用户扫描该码进入小程序后，将统一打开首页，开发者需在对应页面根据获取的码中 scene 字段的值，再做处理逻辑。
     * 使用如下代码可以获取到二维码中的 scene 字段的值。
     * 调试阶段可以使用开发工具的条件编译自定义参数 scene=xxxx 进行模拟，开发工具模拟时的 scene 的参数值需要进行 urlencode
     * </pre>
     *
     * @return 文件内容字节数组
     * @throws WxErrorException 异常
     */
    public R<String> qrCodeBase64(String appId,
                                  QrCodeGenerateParam param) {
        if (!wxMaService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        if (StringUtil.isEmpty(param.getEnvVersion())) {
            param.setEnvVersion("release");
        }
        try {
            WxMaCodeLineColor wxMaCodeLineColor = new WxMaCodeLineColor();
            BeanUtils.copyProperties(param.getLineColor(), wxMaCodeLineColor);

            byte[] wxaCodeUnLimitBytes = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(
                    param.getScene(),
                    param.getPage(), true,
                    param.getEnvVersion(),
                    param.getWidth(),
                    param.getAutoColor() == 0 ? false : true,
                    wxMaCodeLineColor,
                    param.getIsHyaline() == 0 ? false : true);
            return R.ok("获取成功", Base64.getEncoder().encodeToString(wxaCodeUnLimitBytes));
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return R.error(e.toString());
        }
    }


}
