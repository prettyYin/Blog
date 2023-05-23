package com.chandler.blog.service.impl;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.LoginUserDto;
import com.chandler.blog.entity.LoginUser;
import com.chandler.blog.entity.User;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.AdminLoginService;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.JwtUtil;
import com.chandler.blog.util.RedisCache;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Objects;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	RedisCache redisCache;

	/**
	 * 后台登录接口
	 * @param userDto
	 * @return
	 */
	@Override
	public ResponseResult adminLogin(LoginUserDto userDto) {
		if (!StringUtils.hasText(userDto.getUserName())) {
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		}
		User user = BeanCopyUtils.beanCopy(userDto, User.class);
		UsernamePasswordAuthenticationToken authentication
				= new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		Authentication authenticate = authenticationManager.authenticate(authentication);
		// 验证不通过
		if (Objects.isNull(authenticate)) {
			throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
		}
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		// 生成token，存入redis
		String  userId = loginUser.getUser().getId().toString();
		String token = JwtUtil.createJWT(userId);
		redisCache.setCacheObject("adminlogin:" + userId, loginUser);
		HashMap<String, String> map = new HashMap<>();
		map.put("token", token);
		return ResponseResult.okResult(map);
	}

	/**
	 * 后台登出接口
	 * @param token
	 * @return
	 */
	@Override
	public ResponseResult adminLogout(String token) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		Long userId = SecurityUtils.getUserId();
		redisCache.deleteObject("adminlogin:" + userId);
		return ResponseResult.okResult();
	}
}
