package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Category;
import com.chandler.blog.entity.dto.AdminAddCategoryDto;
import com.chandler.blog.entity.dto.AdminUpdateCategoryDto;
import com.chandler.blog.entity.vo.AdminCategoryVo;
import com.chandler.blog.entity.vo.CategoryVo;
import com.chandler.blog.entity.vo.PageVo;

import javax.servlet.http.HttpServletResponse;

/**
* @author Chandler
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2022-06-10 00:13:32
*/
public interface CategoryService extends IService<Category> {

	ResponseResult getCategoryList();

	ResponseResult<CategoryVo> listAllCategory();

	void exportByExcel(HttpServletResponse response) throws Throwable;

	ResponseResult<PageVo> queryCategoryList(Integer pageNum, Integer pageSize, String name, String status);

	ResponseResult addCategory(AdminAddCategoryDto adminAddCategoryDto);

	ResponseResult<AdminCategoryVo> echoCategoryById(Long id);

	ResponseResult updateCategory(AdminUpdateCategoryDto adminUpdateCategoryDto);

	ResponseResult deleteCategory(Long id);
}
