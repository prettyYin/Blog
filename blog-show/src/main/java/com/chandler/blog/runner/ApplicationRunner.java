package com.chandler.blog.runner;

import com.alibaba.fastjson.JSON;
import com.chandler.blog.entity.Article;
import com.chandler.blog.mapper.ArticleMapper;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApplicationRunner implements CommandLineRunner {

	@Autowired
	RedisCache redisCache;

	@Autowired
	ArticleMapper articleMapper;

	@Override
	public void run(String... args) {
//		System.out.println("程序初始化时会执行该部分代码");
		// 查询文章信息，转化为id和viewCount的Map集合
		List<Article> articles = articleMapper.selectList(null);
		Map<String, Integer> viewCountMap = articles.stream()
				.collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
		// 数据库中的id和viewCount存入到redis
		redisCache.setCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_KEY,viewCountMap);
		Map<String, Object> map = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_KEY);
		System.out.println(JSON.toJSONString(map));
		System.out.println("redis存储成功！");
	}
}
