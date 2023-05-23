package com.chandler.blog.controller;


import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.ArticleDto;
import com.chandler.blog.entity.vo.CategoryVo;
import com.chandler.blog.entity.vo.TagVo;
import com.chandler.blog.service.ArticleService;
import com.chandler.blog.service.CategoryService;
import com.chandler.blog.service.TagService;
import com.chandler.blog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class WriteBlogController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	TagService tagService;

	@Autowired
	UploadService uploadService;

	@Autowired
	ArticleService articleService;

	/**
	 * 查询所有分类接口
	 *
	 * @return
	 */
	@GetMapping("/content/category/listAllCategory")
	public ResponseResult<CategoryVo> listAllCategory() {
		return categoryService.listAllCategory();
	}

	/**
	 * 查询所有标签接口
	 *
	 * @return
	 */
	@GetMapping("/content/tag/listAllTag")
	public ResponseResult<TagVo> listAllTag() {
		return tagService.listAllTag();
	}

	/**
	 * 博文缩略图、内容图片上传
	 *
	 * @param img
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseResult uploadImg(@RequestPart(value = "img") MultipartFile img) {
		return uploadService.uploadImg(img);
	}

	/**
	 * 新增博文（发布或保存草稿）
	 * @return
	 */
	@PostMapping("/content/article")
	public ResponseResult addArticle(@RequestBody ArticleDto articleDto) {
		return articleService.addArticle(articleDto);
	}
}
