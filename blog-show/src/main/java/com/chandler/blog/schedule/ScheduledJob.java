package com.chandler.blog.schedule;

import com.chandler.blog.entity.Article;
import com.chandler.blog.mapper.ArticleMapper;
import com.chandler.blog.service.ArticleService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledJob {

	@Autowired
	ArticleMapper articleMapper;

	@Autowired
	ArticleService articleService;

	@Autowired
	RedisCache redisCache;

	/**
	 * 每隔10分钟向redis更新一次浏览量
	 */
	@Scheduled(cron = "* 0/10 * * * ?")
	public void updateViewCountJob() {
		// redis中获取到浏览量
		Map<String, Integer> viewCountMap
				= redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT_KEY);
		List<Article> articles = viewCountMap.entrySet()
				.stream()
				.map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
				.collect(Collectors.toList());
		// 更新至数据库
		articleService.saveOrUpdateBatch(articles);
	}
}
