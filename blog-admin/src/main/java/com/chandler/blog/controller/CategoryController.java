package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.AdminAddCategoryDto;
import com.chandler.blog.entity.dto.AdminUpdateCategoryDto;
import com.chandler.blog.entity.vo.AdminCategoryVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 分页查询分类列表
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param name     根据分类名称进行模糊查询
	 * @param status   根据状态进行查询
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> queryCategoryList(Integer pageNum, Integer pageSize, String name, String status) {
		return categoryService.queryCategoryList(pageNum, pageSize, name, status);
	}

	/**
	 * 新增分类功能
	 * @param adminAddCategoryDto
	 * @return
	 */
	@PostMapping
	public ResponseResult addCategory(@RequestBody AdminAddCategoryDto adminAddCategoryDto) {
		return categoryService.addCategory(adminAddCategoryDto);
	}

	/**
	 * 回显分类信息，用于修改分类接口
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<AdminCategoryVo> echoCategoryById(@PathVariable("id") Long id) {
		return categoryService.echoCategoryById(id);
	}

	/**
	 * 修改分类接口
	 * @param adminUpdateCategoryDto
	 * @return
	 */
	@PutMapping
	public ResponseResult updateCategory(@RequestBody AdminUpdateCategoryDto adminUpdateCategoryDto) {
		return categoryService.updateCategory(adminUpdateCategoryDto);
	}

	/**
	 * 删除某个分类（逻辑删除）
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult deleteCategory(@PathVariable("id") Long id) {
		return categoryService.deleteCategory(id);
	}
}
