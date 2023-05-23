package com.chandler.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.UserRole;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author chandler
 */
public interface UserRoleMapper extends MppBaseMapper<UserRole> {

	List<Long> getRoleIdsByUserId(Long userId);
}

