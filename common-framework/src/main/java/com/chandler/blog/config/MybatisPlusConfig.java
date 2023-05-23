package com.chandler.blog.config;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.jeffreyning.mybatisplus.base.MppSqlInjector;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import com.github.jeffreyning.mybatisplus.handler.DataAutoFill;
import com.github.jeffreyning.mybatisplus.handler.MppKeyGenerator;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {

	/**
	 * 3.4.0之后版本 MyBatisPlus分页插件配置
	 * @return
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return mybatisPlusInterceptor;
	}

}
