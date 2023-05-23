package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Menu;
import com.chandler.blog.entity.vo.AdminMenuTreeVo;
import com.chandler.blog.entity.vo.AdminMenuVo;
import com.chandler.blog.entity.vo.RoleMenuTreeVo;
import com.chandler.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	/**
	 * 根据状态、菜单名查询菜单列表
	 *
	 * @param status
	 * @param menuName
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<AdminMenuVo> queryMenuList(String status, String menuName) {
		return menuService.queryMenuList(status, menuName);
	}

	/**
	 * 新增菜单接口
	 *
	 * @param menu
	 * @return
	 */
	@PostMapping()
	public ResponseResult addMenu(@RequestBody Menu menu) {
		return menuService.addMenu(menu);
	}

	/**
	 * 回显菜单数据，用于更改
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult echoMenuById(@PathVariable("id") Long id) {
		return menuService.echoMenuById(id);
	}

	/**
	 * 修改菜单接口
	 *
	 * @param menu
	 * @return
	 */
	@PutMapping
	public ResponseResult updateMenu(@RequestBody Menu menu) {
		return menuService.updateMenu(menu);
	}

	/**
	 * 逻辑删除菜单，菜单有子菜单则删除失败
	 * @param menuId
	 * @return
	 */
	@DeleteMapping("/{menuId}")
	public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId) {
		return menuService.deleteMenu(menuId);
	}

	/**
	 * 回显角色所关联的菜单权限，用于新增角色使用
	 * @return
	 */
	@GetMapping("/treeselect")
	public ResponseResult<AdminMenuTreeVo> treeSelect() {
		return menuService.treeSelect();
	}

	/**
	 * 加载对应角色菜单列表树接口
	 * @param id
	 * @return
	 */
	@GetMapping("/roleMenuTreeselect/{id}")
	public ResponseResult<RoleMenuTreeVo> roleMenuTreeselect(@PathVariable("id") Long id) {
		return menuService.roleMenuTreeselect(id);
	}
}
