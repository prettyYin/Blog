package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.UserRole;
import com.github.jeffreyning.mybatisplus.service.IMppService;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author chandler
 */
public interface UserRoleService extends IMppService<UserRole> {

	Long getRoleIdByUserId(Long userId);

	List<Long> getRoleIdsByUserId(Long userId);
}
