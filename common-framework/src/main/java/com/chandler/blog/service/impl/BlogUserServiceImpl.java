package com.chandler.blog.service.impl;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.LoginUser;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.vo.BlogLoginUserInfoVo;
import com.chandler.blog.entity.vo.UserInfoVo;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.BlogUserService;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.JwtUtil;
import com.chandler.blog.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Chandler
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 */
@Service
public class BlogUserServiceImpl implements BlogUserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisCache redisCache;

	@Override
	public ResponseResult login(User user) {
		// 用户名和密码调用 authenticate() 认证,
		// 但默认从内存中查询，我们应该自定义数据查询方式——从数据库中查询。实现 UserDetailsService 接口即可
		UsernamePasswordAuthenticationToken authentication
				= new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
		Authentication authenticate = authenticationManager.authenticate(authentication);
		// 认证是否通过
		if (Objects.isNull(authenticate)) {
			throw new SystemException(AppHttpCodeEnum.LOGIN_ERROR);
		}
		// 通过jwt生成token，userId 和 userInfo 存入redis中
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		String userId = loginUser.getUser().getId().toString();
		String token = JwtUtil.createJWT(userId);
		UserInfoVo userInfoVo = BeanCopyUtils.beanCopy(loginUser.getUser(), UserInfoVo.class);
		BlogLoginUserInfoVo blogLoginUserInfoVo = new BlogLoginUserInfoVo(token, userInfoVo);
		redisCache.setCacheObject("bloglogin:" + userId, loginUser);
		return ResponseResult.okResult(blogLoginUserInfoVo);
	}

	@Override
	public ResponseResult logout() {
		// SecurityContextHodler 获取 token
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 获取 userId，拼接 redis-key
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		String key = "bloglogin:" + loginUser.getUser().getId();
		// 删除 redis 中的用户 token
		redisCache.deleteObject(key);
		return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功");
	}
}
