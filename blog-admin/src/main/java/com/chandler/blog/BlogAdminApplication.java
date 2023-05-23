package com.chandler.blog;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chandler.blog.mapper")
@EnableMPP
public class BlogAdminApplication {
	public static void main(String[] args) {
		System.out.println("===========AdminService will start=============");
		SpringApplication.run(BlogAdminApplication.class, args);
		System.out.println("===========AdminService has ended=============");
	}
}
