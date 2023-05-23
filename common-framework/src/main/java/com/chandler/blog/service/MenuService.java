package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Menu;
import com.chandler.blog.entity.vo.AdminMenuTreeVo;
import com.chandler.blog.entity.vo.AdminMenuVo;
import com.chandler.blog.entity.vo.MenuVo;
import com.chandler.blog.entity.vo.RoleMenuTreeVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author chandler
 */
public interface MenuService extends IService<Menu> {

	List<String> getPermsByUserId(Long userId);

	List<MenuVo> getMenuVoTreeByUserId(Long userId);

	ResponseResult<AdminMenuVo> queryMenuList(String status, String menuName);

	ResponseResult addMenu(Menu menu);

	ResponseResult echoMenuById(Long id);

	ResponseResult updateMenu(Menu menu);

	ResponseResult deleteMenu(Long menuId);

	ResponseResult<AdminMenuTreeVo> treeSelect();

	ResponseResult<RoleMenuTreeVo> roleMenuTreeselect(Long id);
}
