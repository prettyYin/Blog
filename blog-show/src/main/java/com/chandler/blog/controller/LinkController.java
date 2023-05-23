package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

	@Autowired
	LinkService linkService;

	/**
	 * 查询所有友链信息
	 * @return
	 */
	@GetMapping("/getAllLink")
	public ResponseResult getAllLink() {
		return linkService.getAllLink();
	}
}
