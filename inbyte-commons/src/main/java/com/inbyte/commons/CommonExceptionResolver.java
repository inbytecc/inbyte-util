package com.inbyte.commons;

import com.inbyte.commons.exception.PyrangeNullException;
import com.inbyte.commons.exception.PyrangeParamException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常拦截
 *
 * @author chenjw
 * @date 2020/8/7
 **/
@ControllerAdvice
@Component
public class CommonExceptionResolver {

    /**
     * 请求参数错误
     */
    public static final String Default_Message = "请求参数错误";

    /**
     * 湃橙全局异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = PyrangeParamException.class)
    @ResponseBody
    public R pyrangeParamException(PyrangeParamException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return R.error(
                StringUtil.isEmpty(e.getMessage()) ? Default_Message : e.getMessage()
        );
    }

    /**
     * null 数据错误拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = PyrangeNullException.class)
    @ResponseBody
    public R PyrangeNullException(PyrangeNullException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return R.failure(
                StringUtil.isEmpty(e.getMessage()) ? Default_Message : e.getMessage()
        );
    }
}