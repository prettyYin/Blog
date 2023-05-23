package com.chandler.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan("com.chandler.blog.mapper")
@EnableSwagger2 /* Swagger2开启 */
@Slf4j
@EnableScheduling /* 开启定时任务，@EnableScheduling放在配置类文件 */
public class ChandlerBlogApplication {
	public static void main(String[] args) {
		log.info("==================Service will start===============");
		SpringApplication.run(ChandlerBlogApplication.class,args);
		log.info("==================Service has ended================");
	}
}
