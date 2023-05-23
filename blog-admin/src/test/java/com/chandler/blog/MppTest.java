package com.chandler.blog;

import com.chandler.blog.entity.RoleMenu;
import com.chandler.blog.mapper.RoleMenuMapper;
import com.chandler.blog.service.RoleMenuService;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = BlogAdminApplication.class)
//@EnableMPP
public class MppTest {

	@Autowired
	RoleMenuMapper roleMenuMapper;

	@Test
	public void testMpp() {
//		Long roleId = 13L;
//		List<Long> menuIds = Arrays.asList(2017L,2018L,2019L,2022L);
//		menuIds.add(2017L);
//		menuIds.add(2018L);
//		menuIds.add(2019L);
//		menuIds.add(2022L);

//		List<RoleMenu> roleMenuList = menuIds
//				.stream()
//				.map(menuId -> new RoleMenu(roleId, menuId))
//				.collect(Collectors.toList());
		RoleMenu roleMenu = roleMenuMapper.selectByMultiId(new RoleMenu(3L, 1059L));
		System.out.println(roleMenu);
	}
}
