package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	/**
	 * 查询文章列表接口
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param title
	 * @param summary
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> queryArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
		return articleService.queryArticleList(pageNum, pageSize, title, summary);
	}

	/**
	 * 指定查询文章详情接口
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<Article> getArticle(@PathVariable("id") Long id) {
		return articleService.getArticle(id);
	}

//	@

	/**
	 * 修改文章内容接口
	 *
	 * @return
	 */
	@PutMapping
	public ResponseResult updateArticle(@RequestBody Article article) {
		return articleService.updateArticle(article);
	}

	/**
	 * 逻辑删除文章接口
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult deleteArticle(@PathVariable("id") Long id) {
		return articleService.deleteArticle(id);
	}
}
