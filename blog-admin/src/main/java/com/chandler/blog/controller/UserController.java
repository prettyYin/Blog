package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.AdminAddUserDto;
import com.chandler.blog.entity.dto.AdminUpdateUserDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.vo.AdminUserRoleVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.service.RoleService;
import com.chandler.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/system/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * 用户分页列表接口
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param userName    用户名模糊搜索
	 * @param phonenumber 手机号的搜索
	 * @param status      状态的查询
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> queryUserList(Integer pageNum, Integer pageSize,
												String userName, String phonenumber, String status) {
		return userService.queryUserList(pageNum, pageSize, userName, phonenumber, status);
	}


	/**
	 * 改变用户状态
	 * @param userStatusDto
	 * @return
	 */
	@PutMapping("/changeStatus")
	public ResponseResult changeStatus(@RequestBody UserStatusDto userStatusDto) {
		return userService.changeStatus(userStatusDto);
	}

	/**
	 * 新增用户功能。新增用户时可以直接关联角色
	 *
	 * @param adminAddUserDto
	 * @return
	 */
	@PostMapping
	public ResponseResult addUser(@RequestBody AdminAddUserDto adminAddUserDto) {
		return userService.addUser(adminAddUserDto);
	}

	/**
	 * 逻辑删除用户
	 *
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult deleteUser(@PathVariable("id") Long id) {
		return userService.deleteUser(id);
	}

	/**
	 * 根据id回显用户所关联的角色,用户修改用户接口
	 *
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<AdminUserRoleVo> echoRolesByUserId(@PathVariable("id") Long userId) {
		return userService.echoUserRolesByUserId(userId);
	}

	/**
	 * 更新用户信息接口
	 * @param adminUpdateUserDto
	 * @return
	 */
	@PutMapping
	public ResponseResult updateUser(@RequestBody AdminUpdateUserDto adminUpdateUserDto) {
		return userService.updateUser(adminUpdateUserDto);
	}
}
