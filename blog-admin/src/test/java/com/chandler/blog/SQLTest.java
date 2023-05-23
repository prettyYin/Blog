package com.chandler.blog;

import com.chandler.blog.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;

import java.util.List;

@SpringBootTest(classes = BlogAdminApplication.class)
@Component
public class SQLTest {

	@Autowired
	private MenuMapper menuMapper;

	@Test
	public void testMenuMapper() {
		List<String> perms = menuMapper.getPermsByUserId(1L);
		System.out.println(perms);
	}
}
