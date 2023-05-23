package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.dto.AddRoleDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.dto.UpdateRoleDto;
import com.chandler.blog.entity.vo.AdminRoleVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.entity.vo.RoleStatusDto;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author chandler
 */
public interface RoleService extends IService<Role> {

	List<String> getRoleNameByUserId(Long userId);

	ResponseResult<PageVo> queryRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

	ResponseResult addRole(AddRoleDto addRoleDto);

	ResponseResult<AdminRoleVo> echoRoleById(Long id);

	ResponseResult updateRole(UpdateRoleDto updateRoleDto);

	ResponseResult deleteRole(Long id);

	ResponseResult<Role> echoAllRole();

	List<Role> getAllRoles();

	ResponseResult changeStatus(RoleStatusDto roleStatusDto);
}
