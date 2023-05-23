package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.UserRole;
import com.chandler.blog.mapper.UserRoleMapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.UserRoleService;

import java.util.List;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author chandler
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends MppServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Autowired
	UserRoleMapper userRoleMapper;

	@Override
	public Long getRoleIdByUserId(Long userId) {
		LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(UserRole::getUserId, userId);
		UserRole userRole = getOne(wrapper);
		return userRole.getRoleId();
	}

	@Override
	public List<Long> getRoleIdsByUserId(Long userId) {
		return userRoleMapper.getRoleIdsByUserId(userId);
	}
}

