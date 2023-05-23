package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "文章分类", description = "文章分类相关接口")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@ApiOperation(value = "获取文章分类列表")
	@GetMapping("/getCategoryList")
	public ResponseResult getCategoryList() {
		return categoryService.getCategoryList();
	}
}
