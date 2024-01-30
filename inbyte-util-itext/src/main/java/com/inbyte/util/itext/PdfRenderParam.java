package com.inbyte.util.itext;

import com.alibaba.fastjson2.JSONObject;
import lombok.*;

/**
 * 渲染 PDF 参数
 *
 * @author chenjw
 * @date 2023-08-02 14:02:24
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfRenderParam {

    /**
     * 模板地址
     */
    private String templateUrl;

    /**
     * 公章地址
     */
    private String sealUrl;

    /**
     * 文档标题
     */
    private String title = "课程合同";

    /**
     * 文档主题
     */
    private String subject = "课程合同";

    /**
     * 模板数据
     */
    private JSONObject data;

}
