package com.inbyte.commons;

import com.inbyte.commons.api.SystemAlarm;
import com.inbyte.commons.exception.BizException;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.model.dto.ResultStatus;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.commons.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * 异常拦截
 *
 * @author chenjw
 * @date 2020/8/7
 **/
@ControllerAdvice
@Component
@Slf4j
public class CommonExceptionResolver {

    @Autowired
    private SystemAlarm alarm;

    /**
     * 请求参数错误
     */
    public static final String Default_Message = "服务器异常, 休息一下, 马上回来";

    /**
     * 未知异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R globalException(Exception e) {
        alarm.alert("未知异常", WebUtil.getRequestInfo(), e);
        log.error("未知异常", e);
        return R.error("休息一下, 马上回来");
    }

    /**
     * inbyte错误拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = InbyteException.class)
    @ResponseBody
    public R globalException(InbyteException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return R.failure(
                StringUtil.isEmpty(e.getMessage()) ? Default_Message : e.getMessage()
        );
    }
    /**
     * Inbyte异常拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public R bizExceptionException(BizException e) {
        if (e.getResult() != null) {
            return e.getResult();
        }
        return R.error(
                StringUtil.isEmpty(e.getMessage()) ? Default_Message : e.getMessage()
        );
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public R paramExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息, 如果存在就使用异常中的消息, 否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数, 按正常逻辑, 只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);

                return R.failure(
                        StringUtil.isEmpty(fieldError.getDefaultMessage())
                                ? fieldError.getField() + " 参数错误"
                                : fieldError.getDefaultMessage()
                );
            }
        }
        return R.failure("请求参数错误");
    }

    /**
     * JSR303 数据校验不通过错误拦截
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public R dataBindException(BindException e) {
        return R.set(ResultStatus.Failure, e.getFieldError().getDefaultMessage());
    }

    /**
     * 400 Bad Request
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public R requestParameterException(MissingServletRequestParameterException e) {
        return R.failure("请求参数不正确, 缺少" + e.getParameterName());
    }


    /**
     * HTTP Status 405 Method Not Support
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public R methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return R.set(ResultStatus.Method_Not_Allowed, "405 - 程序版本可能过旧, 请更新版本或强制刷新页面试下");
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return ResponseResult
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.warn("请求体数据无法读取异常:{}", e.getMessage());
        return R.failure("请求体数据不可读异常, 技术人员已经介入, 马上处理");
    }

    /**
     * 数据库主键重复异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    @ResponseBody
    public R duplicateKeyException(DuplicateKeyException e) {
        log.warn("主键重复异常", e);
        return R.failure("数据库操作失败, 请重新输入或联系技术客服");
    }

    /**
     * 数据处理失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public R dtaIntegrityViolationException(DataIntegrityViolationException e) {
        alarm.alert("Mysql数据处理", WebUtil.getRequestInfo(), e);
        log.warn("数据处理失败", e);
        return R.failure("数据处理失败, 技术人员已介入处理, 请稍后再试");
    }

    /**
     * 数据库操作失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public R sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
//        if (!SpringContextUtil.devEnv()) {
//            exceptionNoticeApi.sendEx(e, LogUtil.getRequestInfo(),30L);
//        }
        alarm.alert("Mysql数据操作不符合要求", WebUtil.getRequestInfo(), e);
        log.warn("字段不能为空异常", e);
        return R.failure("数据库操作失败, 请重新输入或联系技术客服");
    }

    /**
     * http Content-Type类型不匹配错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public R dtaIntegrityViolationException(HttpMediaTypeNotSupportedException e) {
        log.warn("请求头数据类型不符合要求", e);
        return R.failure("请求头数据类型不符合要求");
    }

    /**
     * 请求参数类型不正确
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return R.failure("请求数据类型不符合要求" + e.getName() + ":" + e.getParameter());
    }

}