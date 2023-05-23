package com.chandler.blog.filter;

import com.alibaba.fastjson.JSON;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.LoginUser;
import com.chandler.blog.util.JwtUtil;
import com.chandler.blog.util.RedisCache;
import com.chandler.blog.util.SecurityUtils;
import com.chandler.blog.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private RedisCache redisCache;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 1.获取token
		String token = request.getHeader("token");
		// 如果token不存在，放行执行后续的filter
		if (!StringUtils.hasText(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 2.解析token，获取userId
		Claims claims = null;
		try {
			claims = JwtUtil.parseJWT(token);
		} catch (Exception e) {
			// 获取不到token，响应给前端提示重新登录
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
			WebUtils.renderString(response,JSON.toJSONString(result));
			return;
		}
		String userId = claims.getSubject();
		// 3.拼接userId，从redis中获取用户信息
		LoginUser loginUser = redisCache.getCacheObject("adminlogin:" + userId);
		if (Objects.isNull(loginUser)) { // loginUser为空说明token过期
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
			WebUtils.renderString(response, JSON.toJSONString(result));
			return;
		}
		// 4.存入SecurityContextHolder
		UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(loginUser, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request,response);
	}
}
