package com.chandler.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chandler.blog.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author chandler
 */
public interface RoleMapper extends BaseMapper<Role> {

	List<String> getRoleNameByUserId(Long userId);
}

