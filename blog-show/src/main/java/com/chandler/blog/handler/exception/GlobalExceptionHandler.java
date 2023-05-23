package com.chandler.blog.handler.exception;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.handler.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.chandler.blog.controller"})
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 处理 SystemException 异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SystemException.class)
	public ResponseResult systemExceptionHandler(SystemException e) {
		log.error("SystemException.." + e);
		return ResponseResult.errorResult(e.getCode(), e.getMsg());
	}

	/**
	 * 处理 Exception 异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseResult systemExceptionHandler(Exception e) {
		log.error("Exception.." + e);
		// e.getMessage() 方便调试,后期更换
		return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,e.getMessage());
	}
}
