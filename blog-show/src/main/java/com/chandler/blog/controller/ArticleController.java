package com.chandler.blog.controller;


import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.service.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "评论",description = "评论相关接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	/**
	 * 查询热门文章
	 * @return
	 */
	@ApiOperation(value = "查询热门文章列表",httpMethod = "GET")
	@GetMapping("/hotArticleList")
	public ResponseResult hotArticleList() {
		return articleService.hotArticleList();
	}

	/**
	 * 分页查询博文列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有文章列表", httpMethod = "GET")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "pageNum", value = "页号"),
					@ApiImplicitParam(name = "pageSize",value = "每页显示的内容"),
					@ApiImplicitParam(name = "categoryId",value = "文章类型id")
			}
	)
	@GetMapping("/articleList")
	public ResponseResult<PageVo> articleList(Long pageNum, Long pageSize, Long categoryId) {

		return articleService.queryArticleList(pageNum, pageSize, categoryId);
	}

	@GetMapping("/{id}")
	public ResponseResult getDetailArticle(@PathVariable("id") Long id) {
		return articleService.getDetailArticle(id);
	}

	/**
	 * 更新浏览量
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据文章id更新相应文章浏览量")
	@ApiImplicitParam(name = "id",value = "文章id")
	@PutMapping("/updateViewCount/{id}")
	public ResponseResult updateViewCount(@PathVariable("id") Long id) {
		return articleService.updateViewCount(id);
	}
}
