package com.chandler.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author chandler
 * @since 2023-03-30 21:23:12
 */
public interface MenuMapper extends BaseMapper<Menu> {

	List<String> getPermsByUserId(Long userId);

	List<Menu> getMenusByUserId(Long userId);
}

