package com.inbyte.util.weixin.mp.client;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.shortlink.GenerateShortLinkRequest;
import cn.binarywang.wx.miniapp.bean.urllink.GenerateUrlLinkRequest;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSON;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信小程序 URL Link
 *
 * 微信小程序文档：https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/qrcode-link/short-link/generateShortLink.html
 * @author chenjw
 */
@Slf4j
@Component
public class WxMpLinkClient {

    @Autowired
    private WxMaService wxMaService;

    /**
     * 生成 URL Link
     */
    public R<String> generateUrlLink(String appId,
                                     @NotNull String path,
                                     String query) {
        if (!wxMaService.switchover(appId)) {
            log.error("URL Link 生成, 未找到对应appId={}, 的配置, 请核实！", appId);
            return R.failure("服务错误, 稍等一下马上就好");
        }

        try {
            GenerateUrlLinkRequest generateUrlLinkRequest = GenerateUrlLinkRequest.builder()
                    .path(path)
                    .query(query)
                    .envVersion("release")
                    .build();
            log.info("URL Link 生成, jsCode:{}", JSON.toJSONString(generateUrlLinkRequest));
            String generate = wxMaService.getLinkService().generateUrlLink(generateUrlLinkRequest);
            log.info("URL Link 生成, 返回结果:{}", generate);
            return R.ok("生成成功", generate);
        } catch (WxErrorException e) {
            log.error("URL Link 生成", e);
            return R.failure("URL Link 生成, 稍等一下马上就好");
        } finally {
            //清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

    /**
     * 生成 URL Link
     */
    public R<String> generateShortLink(String appId,
                                       @NotNull String pageUrl,
                                       String pageTitle,
                                       WhetherDict permanent) {
        if (!wxMaService.switchover(appId)) {
            log.error("URL Link 生成, 未找到对应appId={}, 的配置, 请核实！", appId);
            return R.failure("服务错误, 稍等一下马上就好");
        }

        try {
            GenerateShortLinkRequest generateUrlLinkRequest = GenerateShortLinkRequest.builder()
                    .pageUrl(pageUrl)
                    .pageTitle(pageTitle)
                    .isPermanent(permanent.yes())
                    .build();
            log.info("URL Link 生成, jsCode:{}", JSON.toJSONString(generateUrlLinkRequest));
            String generate = wxMaService.getLinkService().generateShortLink(generateUrlLinkRequest);
            log.info("URL Link 生成, 返回结果:{}", generate);
            return R.ok("生成成功", generate);
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 43104) {
                return R.failure("此小程序暂时没有权限生成短链");
            }
            log.error("URL Link 生成", e);
            return R.failure("URL Link 生成, 稍等一下马上就好");
        } finally {
            //清理ThreadLocal
            WxMaConfigHolder.remove();
        }
    }

}
