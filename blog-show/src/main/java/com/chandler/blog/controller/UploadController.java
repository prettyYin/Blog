package com.chandler.blog.controller;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	@Autowired
	UploadService uploadService;

	/**
	 * 头像上传
	 * @param token
	 * @param img
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseResult uploadAvatar(@RequestHeader(value = "token",required = true) String token,
									   @RequestPart(value = "img") MultipartFile img) {
		if (!StringUtils.hasText(token))
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		// 判断文件类型
		String originalFilename = img.getOriginalFilename();
		if (!originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".png"))
			throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
		return uploadService.uploadAvatar(img);
	}
}
