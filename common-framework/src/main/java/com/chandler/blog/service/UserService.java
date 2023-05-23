package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.User;
import com.chandler.blog.entity.dto.AdminAddUserDto;
import com.chandler.blog.entity.dto.AdminUpdateUserDto;
import com.chandler.blog.entity.dto.UserStatusDto;
import com.chandler.blog.entity.vo.AdminUserRoleVo;
import com.chandler.blog.entity.vo.PageVo;
import com.github.jeffreyning.mybatisplus.service.IMppService;


/**
 * 用户表(User)表服务接口
 *
 * @author chandler
 * @since
 */
public interface UserService extends IService<User> {

	ResponseResult userInfo(String token);

	ResponseResult updateUserInfo(String token, User user);

	ResponseResult register(User user);

	ResponseResult changeStatus(UserStatusDto userStatusDto);

	ResponseResult<PageVo> queryUserList(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

	ResponseResult addUser(AdminAddUserDto adminAddUserDto);

	ResponseResult deleteUser(Long id);

	ResponseResult<AdminUserRoleVo> echoUserRolesByUserId(Long userId);

	ResponseResult updateUser(AdminUpdateUserDto adminUpdateUserDto);
}
