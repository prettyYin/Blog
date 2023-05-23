package com.chandler.blog.handler.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SystemException.class)
	@ResponseBody
	public ResponseResult handlerBusinessException(SystemException e) {
		e.printStackTrace(); // 打印，方便调试
		return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMsg());
	}

	/**
	 * 其他异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult handlerException(Exception e) {
		e.printStackTrace(); // 打印，方便调试
		return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR, e.getMessage());
	}
}
