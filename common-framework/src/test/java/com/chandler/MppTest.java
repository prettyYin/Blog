package com.chandler;

import com.chandler.blog.entity.RoleMenu;
import com.chandler.blog.service.RoleMenuService;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

//@SpringBootTest(classes = BlogAdminApplication.class)
@EnableMPP
public class MppTest {

	@Autowired
	RoleMenuService roleMenuService;

	@Test
	public void testMpp() {
		Long roleId = 13L;
		List<Long> menuIds = null;
		menuIds.add(2017L);
		menuIds.add(2018L);
		menuIds.add(2019L);
		menuIds.add(2022L);

		List<RoleMenu> roleMenuList = menuIds
				.stream()
				.map(menuId -> new RoleMenu(roleId, menuId))
				.collect(Collectors.toList());
		boolean b = roleMenuService.saveOrUpdateBatch(roleMenuList);
		System.out.println(b);
	}
}
