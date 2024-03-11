package com.inbyte.util.itext;

import com.alibaba.fastjson2.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Iterator;

@Slf4j
public class PdfUtil {

    // 创建字体显示中文
    public static BaseFont Base_Font_Chinese;

    static {
        try {
            Base_Font_Chinese = BaseFont.createFont("/usr/share/fonts/msyh.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            String fontPath = "d:\\tmp\\微软雅黑.ttf";
//            String fontPath = "C:\\Windows\\Fonts\\STSONG.ttc";
//            String fontPath = "C:\\Windows\\Fonts\\微软雅黑.ttf";
        } catch (Exception e) {
            log.warn("加载字体失败, 可能影响文档相关操作");
        }
    }

    public static ByteArrayOutputStream renderPdfTemplate(PdfRenderParam param) {

        JSONObject data = param.getData();
        if (data == null) {
            param.setData(new JSONObject());
        }
        // 获取当前日期时间

        // 使用DateTimeFormatter格式化日期时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日");
        LocalDate now = LocalDate.now();
        data.put("date", now.format(formatter));
        data.put("year", now.getYear());
        data.put("month", now.getMonthValue());
        data.put("day", now.getDayOfMonth());

        // 利用模板生成pdf
        // 模板路径
        // 生成的新文件路径
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfReader reader = null;
        ByteArrayOutputStream bos = null;
        Document document = null;
        PdfStamper stamper;
        try {
            // 读取pdf模板
            reader = new PdfReader(new URL(param.getTemplateUrl()));
//            reader = new PdfReader(param.getTemplateUrl());
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);

            //查询出模板文件的表单域
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next();
                if (!"seal".equals(name) && !"customerSignature".equals(name)) {
                    form.addSubstitutionFont(Base_Font_Chinese);
                    form.setFieldProperty("name", "textfont", Base_Font_Chinese, null);
                    form.setField(name, data.getString(name));
                }
            }
            // 如果为 false 那么生成的 PDF 文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            // 1.创建一个 document
            document = new Document();

            // 公章
            AcroFields.FieldPosition fieldPosition = form.getFieldPositions("seal").get(0);
            Image instance = Image.getInstance(new URL(param.getSealUrl()));
            instance.scaleAbsolute(fieldPosition.position.getWidth(), fieldPosition.position.getHeight());
            instance.setAbsolutePosition(fieldPosition.position.getLeft(), fieldPosition.position.getBottom());
            PdfContentByte overContent = stamper.getOverContent(fieldPosition.page);
            overContent.addImage(instance);

            // 签名
            String customerSignature = data.getString("customerSignature");
            int index = customerSignature.indexOf(",");
            if (index != -1) {
                customerSignature = customerSignature.substring(index+1);
            }
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] customerSignatures = decoder.decode(customerSignature);
            fieldPosition = form.getFieldPositions("customerSignature").get(0);
            Image image = Image.getInstance(customerSignatures);
            image.scaleAbsolute(fieldPosition.position.getWidth(), fieldPosition.position.getHeight());
            image.setAbsolutePosition(fieldPosition.position.getLeft(), fieldPosition.position.getBottom());
            overContent = stamper.getOverContent(fieldPosition.page);
            overContent.addImage(image);

            // 设置压缩
            // PdfStamper 需要关闭后, document 才能读取到页面信息
            stamper.close();
            // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
            // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径
            /*PdfWriter writer = PdfWriter.getInstance(document, out);
            //PDF版本(默认1.4)
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_6);
            */

            // 3.打开文档 --> 写入数据之前要打开文档
            PdfCopy pdfCopy = new PdfCopy(document, out);
//            pdfCopy.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            document.open();
            // 设置属性
            // 标题
            document.addTitle(param.getTitle());
            // 主题
            document.addSubject(param.getSubject());
            // 创建时间
            document.addCreationDate();
            // 作者
            document.addAuthor("湃橙体育科技");
            // 应用程序
            document.addCreator("湃橙体育科技");

            // 4.添加内容段落
            //获取pdf页数
            int pageNum = reader.getNumberOfPages();
            for (int i = 0; i < pageNum; i++) {
                PdfImportedPage importPage = pdfCopy.getImportedPage(new PdfReader(bos.toByteArray()), i + 1);
                pdfCopy.addPage(importPage);
            }

        } catch (IOException e) {
            log.error("PDF 文件渲染 IO 异常", e);
        } catch (DocumentException e) {
            log.error("PDF 文件渲染操作异常", e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (reader != null) {
                    reader.close();

                }
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                log.error("PDF 文件流关闭异常", e);
            }
        }
        return out;
    }

    /**
     * 测试用
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JSONObject data = new JSONObject();
        //模板的地址和新pdf的存储地址
        //分期合同信息
        data.put("studentName", "啊啊啊啊");
        data.put("parentName", "啊鹅鹅鹅鹅鹅a");
        data.put("customerSignature", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEEAAAApCAYAAABqUERyAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAF8SURBVGhD7ZYxTgMxEEU5AiVVOmgQDR3HoKahpaHMAeig4gY0XAGJU6WzBCGKGSOQVpvvsT3xkIX84lXZHfs/OztzEEKI+w4lCJQgUIJACQIlCJQgUIJACYK7hLfXl7i+OItxdthMeu/9+QnW7YmLhG2CI7xldJGQNtgzNOT4yE2ESYLlpD/mt7BWjuXjw2YdJxFVEqzXuzU4YkOGgwhVQkv41c11DIsFrLMtSAR6zooqoSSgx0nXMhaBnrGiShgv/JuhEcO9oN+tmD6Mu4IShH8jofcgVUtaM9dV3CTsKmwtQyndJKSCUw4N+W61XSQs7+/wIgq5TlOSaelQcPoc1DJLqL3u2qZb/jJeg1iiWYK28dXVpbpZ63fCez6pllC6ptrY3BrecwRHFCWUAoxPyXLa3iddQpXwFeh0lt34VK93K6qE9fkJDGFlauF/UCWkPorC1DDVwAhVQq6/jvlLgRHFD+M+QAkCJQiUIFCCQAkCJQiUIFCCQAkhxE9R8Gx8r/NO5gAAAABJRU5ErkJggg==");

        data.put("venueName", "123东方启明星未来之光校区x");
        data.put("venueAddress", "天荟未来之光城6号楼204（地铁2号线杜甫村站B口200米）");
        data.put("companyName", "易思网络");
        data.put("orderNo", "CRS081100104232173947");
        data.put("courseName", "精英私教班东方");
        data.put("classHour", 66);
        data.put("paymentAmount", 66666.66);

        PdfRenderParam param = PdfRenderParam.builder()
                .title("标题")
                .subject("主题")
                .templateUrl("https://pyrange.oss-cn-hangzhou.aliyuncs.com/mct-space/pyrange-sport/venue-management/contract/super-planet-contract-template.pdf")
//                .templateUrl("D://tmp/test.pdf")
                .sealUrl("https://pyrange-dev.oss-cn-hangzhou.aliyuncs.com/3212d833e0b74909d1aed20cbe1ecb0.png")
                .data(data)
                .build();
        ByteArrayOutputStream out = renderPdfTemplate(param);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D://tmp/out.pdf"));
            fileOutputStream.write(out.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}