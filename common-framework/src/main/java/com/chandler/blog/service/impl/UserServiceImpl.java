package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Role;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.UserRole;
import com.chandler.blog.entity.dto.AdminAddUserDto;
import com.chandler.blog.entity.dto.AdminUpdateUserDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.vo.*;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.mapper.UserMapper;
import com.chandler.blog.service.RoleService;
import com.chandler.blog.service.UserRoleService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import com.chandler.blog.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.chandler.blog.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author chandler
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	RoleService roleService;

	@Override
	public ResponseResult userInfo(String token) {
		// 不要用这种方法，有可能用户登录后修改了个人信息，因此读出来的数据是脏数据，建议从数据库中查询信息后封装返回
//		LoginUser loginUser = (LoginUser) SecurityUtils.getAuthentication().getPrincipal();
//		BeanCopyUtils.beanCopy(loginUser, UserInfoVo.class);
		Long userId = SecurityUtils.getUserId();
		User user = getById(userId);
		UserInfoVo userInfoVo = BeanCopyUtils.beanCopy(user, UserInfoVo.class);
		return ResponseResult.okResult(userInfoVo);
	}

	@Override
	public ResponseResult updateUserInfo(String token,User user) {
		LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
		// 限制只更新部分数据，防止请求参数过多而修改过多数据
		wrapper
				.set(User::getNickName, user.getNickName())
				.set(User::getSex, user.getSex())
				.set(User::getAvatar, user.getAvatar())
				.eq(User::getId, user.getId());
		update(wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult register(User user) {
		// 数据校验
		if (!StringUtils.hasText(user.getUserName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		else if (!StringUtils.hasText(user.getNickName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
		else if (!StringUtils.hasText(user.getPassword()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
		else if (!StringUtils.hasText(user.getEmail()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
		else if (isExistData(User::getUserName,user.getUserName()))
			throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
		else if (isExistData(User::getNickName,user.getNickName()))
			throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
		else if (isExistData(User::getEmail,user.getEmail()))
			throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
		// 密码加密
		String encodePwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePwd);
		// 保存用户数据
		save(user);
		return ResponseResult.okResult();
	}


	@Override
	public ResponseResult changeStatus(UserStatusDto userStatusDto) {
		Long userId = SecurityUtils.getUserId();
		LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(User::getId, userStatusDto.getUserId())
				.set(User::getStatus, userStatusDto.getStatus());
		update(null, wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<PageVo> queryUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(userName), User::getUserName, userName)
				.eq(StringUtils.hasText(phonenumber), User::getPhonenumber, phonenumber)
				.eq(StringUtils.hasText(status), User::getStatus, status);
		Page<User> page = new Page<>(pageNum,pageSize);
		page(page, wrapper);
		List<User> users = page.getRecords();
		List<AdminUserVo> adminUserVos = BeanCopyUtils.beanCopyList(users, AdminUserVo.class);
		PageVo pageVo = new PageVo(adminUserVos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Transactional
	@Override
	public ResponseResult addUser(AdminAddUserDto adminAddUserDto) {
		// 1.合法性校验
		if (!StringUtils.hasText(adminAddUserDto.getUserName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		else if (!StringUtils.hasText(adminAddUserDto.getNickName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
		else if (!StringUtils.hasText(adminAddUserDto.getPassword()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
		else if (!StringUtils.hasText(adminAddUserDto.getEmail()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
		else if (isExistData(User::getUserName,adminAddUserDto.getUserName()))
			throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
		else if (isExistData(User::getNickName,adminAddUserDto.getNickName()))
			throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
		else if (isExistData(User::getEmail,adminAddUserDto.getEmail()))
			throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
		else if (isExistData(User::getPhonenumber,adminAddUserDto.getPhonenumber()))
			throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
		// 2.保存用户信息
		String encodePwd = passwordEncoder.encode(adminAddUserDto.getPassword());
		adminAddUserDto.setPassword(encodePwd);// 密码加密存储
		User user = BeanCopyUtils.beanCopy(adminAddUserDto, User.class);
		save(user);
		// 3.保存用户-角色关联
		List<UserRole> userRoleList = adminAddUserDto.getRoleIds()
				.stream()
				.map(roleId -> new UserRole(user.getId(), roleId))
				.collect(Collectors.toList());
		userRoleService.saveOrUpdateBatchByMultiId(userRoleList);
		return ResponseResult.okResult(userRoleList);
	}

	@Override
	public ResponseResult deleteUser(Long id) {
		LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(User::getId, id)
				.set(User::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		update(null, wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<AdminUserRoleVo> echoUserRolesByUserId(Long userId) {
		// 根据userId查询roleIds
		List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
		// 查询所有角色信息
		List<Role> roles = roleService.getAllRoles();
		// 根据userId查询用户信息，拷贝为AdminUserInfoVo
		User user = getById(userId);
		AdminUserInfoVo adminUserInfoVo = BeanCopyUtils.beanCopy(user, AdminUserInfoVo.class);
		AdminUserRoleVo adminUserRoleVo = new AdminUserRoleVo(roleIds, roles, adminUserInfoVo);
		return ResponseResult.okResult(adminUserRoleVo);
	}

	@Transactional
	@Override
	public ResponseResult updateUser(AdminUpdateUserDto adminUpdateUserDto) {
		// 1.合法性校验
		if (!StringUtils.hasText(adminUpdateUserDto.getUserName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
		else if (!StringUtils.hasText(adminUpdateUserDto.getNickName()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
		else if (!StringUtils.hasText(adminUpdateUserDto.getEmail()))
			throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
		// 2.更新用户信息
		User user = BeanCopyUtils.beanCopy(adminUpdateUserDto, User.class);
		updateById(user);
		// 3.更新用户-角色关联
		// 删除旧关联
		Long userId = adminUpdateUserDto.getId();
		List<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
		roleIds
				.stream()
				.map(roleId -> new UserRole(adminUpdateUserDto.getId(), roleId))
				.forEach(userRole -> userRoleService.deleteByMultiId(userRole));
		// 保存新关联
		List<UserRole> userRoleList = adminUpdateUserDto.getRoleIds()
				.stream()
				.map(roleId -> new UserRole(userId, roleId))
				.collect(Collectors.toList());
		userRoleService.saveOrUpdateBatchByMultiId(userRoleList);
		return ResponseResult.okResult();
	}

	/**
	 * 查询user表中column字段是否包含data值
	 * @param column
	 * @param data
	 * @return
	 */
	private boolean isExistData(SFunction<User,?> column,String data) {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(column, data);
		User user = getOne(wrapper);
		if (null != user)
			return true;
		return false;
	}
}

