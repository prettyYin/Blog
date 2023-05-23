package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.dto.ArticleDto;
import com.chandler.blog.entity.vo.PageVo;

/**
* @author Chandler
* @description 针对表【sg_article(文章表)】的数据库操作Service
*/
public interface ArticleService extends IService<Article> {

	ResponseResult hotArticleList();

	ResponseResult<PageVo> queryArticleList(Long pageNum, Long pageSize, Long categoryId);

	ResponseResult<PageVo> queryArticleList(Integer pageNum, Integer pageSize, String title, String summary);

	ResponseResult getDetailArticle(Long id);

	ResponseResult updateViewCount(Long id);

	ResponseResult addArticle(ArticleDto articleDto);

	ResponseResult<Article> getArticle(Long id);

	ResponseResult updateArticle(Article article);

	ResponseResult deleteArticle(Long id);
}
