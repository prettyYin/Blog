package com.chandler.blog.service;

import com.chandler.blog.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	ResponseResult uploadAvatar(MultipartFile img);

	ResponseResult uploadImg(MultipartFile img);
}
