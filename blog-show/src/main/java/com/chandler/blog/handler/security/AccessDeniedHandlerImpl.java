package com.chandler.blog.handler.security;

import com.alibaba.fastjson.JSON;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败异常处理
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
		e.printStackTrace(); // 打印测试，方便调试
		ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
		WebUtils.renderString(response, JSON.toJSONString(result));
	}
}
