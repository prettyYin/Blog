package com.chandler.blog.service;

import com.chandler.blog.ResponseResult;

public interface AdminInfoService {
	ResponseResult getInfo(String token) throws Exception;

	ResponseResult getRouters(String token);
}
