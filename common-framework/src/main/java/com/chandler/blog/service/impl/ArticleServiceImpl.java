package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.ArticleTag;
import com.chandler.blog.entity.Category;
import com.chandler.blog.entity.dto.ArticleDto;
import com.chandler.blog.entity.vo.*;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.mapper.ArticleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.service.ArticleService;
import com.chandler.blog.service.ArticleTagService;
import com.chandler.blog.service.CategoryService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Chandler
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
		implements ArticleService {

	@Autowired
	ArticleService articleService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	RedisCache redisCache;

	@Autowired
	ArticleTagService articleTagService;

	/**
	 * 查询热门文章
	 *
	 * @return
	 */
	@Override
	public ResponseResult hotArticleList() {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
		queryWrapper.orderByDesc(Article::getViewCount);
		Page<Article> page = new Page<>(1, 10);
		page(page, queryWrapper);
		List<Article> articles = page.getRecords(); //实际查询的全部数据
		// VO优化：bean拷贝
//		ArrayList<HotArticleVO> hotArticleVOs = new ArrayList<>(); // 展示的VO数据
//		for (Article article : articles) {
//			HotArticleVO hotArticleVO = new HotArticleVO();
//			BeanUtils.copyProperties(article,hotArticleVO);
//			hotArticleVOs.add(hotArticleVO);
//		}
		List<HotArticleVo> hotArticleVOs = BeanCopyUtils.beanCopyList(articles, HotArticleVo.class);

		ResponseResult result = ResponseResult.okResult(hotArticleVOs);
		return result;
	}

	/**
	 * 查询文章列表 和 根据id查询对应的分类文章
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param categoryId
	 * @return
	 */
	@Override
	public ResponseResult<PageVo> queryArticleList(Long pageNum, Long pageSize, Long categoryId) {
		// 需求一：首页：查询所有文章列表
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		// 需求二：分类页:根据分类id查询对应分类下的文章
		queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
		// 1.文章必须为正式发布
		queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
		// 2.文章按置顶信息降序排序
		queryWrapper.orderByDesc(Article::getIsTop);
		// 分页查询
		Page<Article> articlePage = new Page<>(pageNum, pageSize);
		page(articlePage, queryWrapper);
		List<Article> articleList = articlePage.getRecords();
		// 根据article表中查询出的categoryId来查询categoryName
		/**
		 * TODO 后续可以使用redis缓存或Map集合来优化此部分
		 */
//		for (Article article : articleList) {
//			Category category = categoryService.getById(article.getCategoryId());
//			String categoryName = category.getName();
//			article.setCategoryName(categoryName);
//		}
		articleList = articleList
				.parallelStream() // 并行流，可以并行查数据库提升效率
				.map(article -> {
					Category category = categoryService.getById(article.getCategoryId());
					return article.setCategoryName(category.getName());
				})
				.collect(Collectors.toList());
		// 封装成ArticleVo对象
		List<ArticleVo> articleVos = BeanCopyUtils.beanCopyList(articleList, ArticleVo.class);
		// 根据接口文档需求，将数据封装成PageVo对象传入前端
		PageVo result = new PageVo(articleVos, articlePage.getTotal());
		return ResponseResult.okResult(result);
	}

	@Override
	public ResponseResult<PageVo> queryArticleList(Integer pageNum, Integer pageSize, String title, String summary) {
		// 模糊查询文章：条件--title，summary
		LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(title), Article::getTitle, title);
		wrapper.like(StringUtils.hasText(summary), Article::getSummary, summary);
		Page<Article> articlePage = new Page<>(pageNum, pageSize);
		page(articlePage, wrapper);
		List<Article> articles = articlePage.getRecords();
		List<AdminArticleVo> adminArticleVos = BeanCopyUtils.beanCopyList(articles, AdminArticleVo.class);
		PageVo pageVo = new PageVo(adminArticleVos, articlePage.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	/**
	 * 查询详情文章信息
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ResponseResult getDetailArticle(Long id) {
		// 根据id查询文章信息
		Article article = articleService.getById(id);
		// 将文章信息封装成Vo对象
		ArticleDetailVo articleDetailVo = BeanCopyUtils.beanCopy(article, ArticleDetailVo.class);
		// 根据分类id设置分类名称
		Category category = categoryService.getById(articleDetailVo.getCategoryId());
		if (Objects.nonNull(articleDetailVo)) {
			articleDetailVo.setCategoryName(category.getName());
		}
		// 封装并返回Json数据
		return ResponseResult.okResult(articleDetailVo);
	}

	/**
	 * 更新浏览量
	 * @param id
	 * @return
	 */
	@Override
	public ResponseResult updateViewCount(Long id) {
		// 指定文章的阅读量自增1
		redisCache.incrCacheMapValue(SystemConstants.ARTICLE_VIEW_COUNT_KEY, id.toString(), SystemConstants.INCR_STEP);
		return ResponseResult.okResult();
	}

	@Override
	@Transactional
	public ResponseResult addArticle(ArticleDto articleDto) {
		if (!StringUtils.hasText(articleDto.getTitle())) {
			throw new SystemException(AppHttpCodeEnum.TITLE_NOT_NULL);
		}
		if (!StringUtils.hasText(articleDto.getContent())) {
			throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
		}
		if (!StringUtils.hasText(articleDto.getSummary())) {
			throw new SystemException(AppHttpCodeEnum.SUMMARY_NOT_NULL);
		}
		if (!StringUtils.hasText(articleDto.getThumbnail())) {
			throw new SystemException(AppHttpCodeEnum.THUMBNAIL_NOT_NULL);
		}
		// 保存文章
		Article article = BeanCopyUtils.beanCopy(articleDto, Article.class);
		save(article);
		// 保存文章和标签的关联
		List<ArticleTag> articleTagList = articleDto.getTags() // 转化为文章和标签的表关系
				.stream()
				.map(tagId -> new ArticleTag(article.getId(), tagId))
				.collect(Collectors.toList());
		articleTagService.saveBatch(articleTagList); // 批量保存，因为article_id--tag_id ==== 1--n
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<Article> getArticle(Long id) {
		Article article = getById(id);
		return ResponseResult.okResult(article);
	}

	@Override
	public ResponseResult updateArticle(Article article) {
		updateById(article);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteArticle(Long id) {
		LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Article::getId, id);
		wrapper.set(Article::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		update(null, wrapper);
		return ResponseResult.okResult();
	}
}
