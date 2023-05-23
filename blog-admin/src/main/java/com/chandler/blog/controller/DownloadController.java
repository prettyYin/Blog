package com.chandler.blog.controller;

import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/content/category")
public class DownloadController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 文件下载并且失败的时候返回json
	 *
	 * @since 2.1.1
	 */
	@PreAuthorize("@perms.hasAuthority('system:user:export')")
	@GetMapping("/export")
	public void downloadFailedUsingJson(HttpServletResponse response){
		try {
			categoryService.exportByExcel(response);
		} catch (Throwable throwable) {
//			throwable.printStackTrace();
			throw new SystemException(AppHttpCodeEnum.NO_CATEGORIES);
		}
	}
}
