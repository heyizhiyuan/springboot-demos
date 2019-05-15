package com.cnj.protocol.handler;

import com.cnj.protocol.exception.BusinessException;
import com.cnj.protocol.i18n.MessageUtils;
import com.cnj.protocol.result.ResultEntity;
import com.cnj.protocol.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Create by cnj on 2019-2-24
 */
@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    MessageUtils messageUtils;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResultEntity<Void> handleException(HttpServletRequest request, HttpServletResponse response, BusinessException ex) {
        log.error("{}",ex);
        if (HttpRequestUtil.isJsonRequest(request)){
            return ResultEntity.fail(ex.getMessage());
        }
        try {
            request.getRequestDispatcher("/error").forward(request,response);
        } catch (IOException | ServletException e) {
            log.error("{}",e);
        }
        return null;
    }

    /**
     * 校验错误拦截处理
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultEntity<Void> validationBodyException(HttpServletRequest request, HttpServletResponse response,MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p ->{
                FieldError fieldError = (FieldError) p;
                log.error(fieldError.getDefaultMessage());
            });
            return ResultEntity.fail(errors.get(0).getDefaultMessage());
        }
        return ResultEntity.fail("参数格式错误");
    }

    /**
     * 参数类型转换错误
     * @param exception 错误
     * @return 错误信息
     *
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResultEntity parameterTypeException(HttpMessageConversionException exception){
        log.error(exception.getCause().getLocalizedMessage());
        return ResultEntity.fail("类型转换错误");
    }


}