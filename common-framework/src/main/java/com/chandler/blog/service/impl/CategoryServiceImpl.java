package com.chandler.blog.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.Category;
import com.chandler.blog.entity.dto.AdminAddCategoryDto;
import com.chandler.blog.entity.dto.AdminUpdateCategoryDto;
import com.chandler.blog.entity.vo.*;
import com.chandler.blog.mapper.CategoryMapper;
import com.chandler.blog.service.ArticleService;
import com.chandler.blog.service.CategoryService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
* @author Chandler
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
implements CategoryService {

	@Autowired
	ArticleService articleService;

	@Override
	public ResponseResult getCategoryList() {
		// 查询文章表中状态为“已发布”的文章
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
		List<Article> articleList = articleService.list(queryWrapper);
		// 获取文章的分类id，并且去重
		Set<Long> categoryIds = articleList
				.stream()
				.map(article -> article.getCategoryId())
				.collect(Collectors.toSet());
		// 查询分类表
		List<Category> categoryList
				= listByIds(categoryIds)
				.stream()
				.filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
				.collect(Collectors.toList());
		// 封装VO
		List<CategoryVo> categoryVos = BeanCopyUtils.beanCopyList(categoryList, CategoryVo.class);

		return ResponseResult.okResult(categoryVos);
	}

	@Override
	public ResponseResult<CategoryVo> listAllCategory() {
		List<Category> categories = list();
		List<CategoryVo> categoryVos = BeanCopyUtils.beanCopyList(categories, CategoryVo.class);
		return ResponseResult.okResult(categoryVos);
	}

	@Override
	public void exportByExcel(HttpServletResponse response) {

		// 查出需要导出的分类数据
		LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
		wrapper.select(Category::getName,Category::getDescription,Category::getStatus);
		List<Category> categories = list(wrapper);
		List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.beanCopyList(categories, ExcelCategoryVo.class);
		// easyexcel输入流获取数据并使用输出流写入响应流中
		try {
			// 没有数据抛出异常
			Optional.ofNullable(categories)
					.orElseThrow(RuntimeException::new);
			WebUtils.setDownLoadHeader("分类", response,".xlsx");
			// 这里需要设置不关闭流
			EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
					.doWrite(excelCategoryVos);
		} catch (Exception e) {
			// 重置response
			response.reset();
			ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.FILE_DOWNLOAD_ERROR);
			WebUtils.renderString(response, JSON.toJSONString(result));
		}
	}

	@Override
	public ResponseResult<PageVo> queryCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
		LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(name), Category::getName, name);
		wrapper.eq(StringUtils.hasText(status), Category::getStatus, status);
		Page<Category> page = new Page<>(pageNum, pageSize);
		page(page, wrapper);
		List<Category> categories = page.getRecords();
		List<AdminCategoryVo> adminCategoryVos = BeanCopyUtils.beanCopyList(categories, AdminCategoryVo.class);
		PageVo pageVo = new PageVo(adminCategoryVos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Override
	public ResponseResult addCategory(AdminAddCategoryDto adminAddCategoryDto) {
		Category category = BeanCopyUtils.beanCopy(adminAddCategoryDto, Category.class);
		save(category);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<AdminCategoryVo> echoCategoryById(Long id) {
		Category category = getBaseMapper().selectById(id);
		AdminCategoryVo adminCategoryVo = BeanCopyUtils.beanCopy(category, AdminCategoryVo.class);
		return ResponseResult.okResult(adminCategoryVo);
	}

	@Override
	public ResponseResult updateCategory(AdminUpdateCategoryDto adminUpdateCategoryDto) {
		Category category = BeanCopyUtils.beanCopy(adminUpdateCategoryDto, Category.class);
		updateById(category);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteCategory(Long id) {
		LambdaUpdateWrapper<Category> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Category::getId, id)
				.set(Category::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		update(null, wrapper);
		return ResponseResult.okResult();
	}
}
