package com.chandler.blog.util;

import com.chandler.blog.entity.Article;
import com.chandler.blog.entity.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

	/**
	 * 单个对象的bean拷贝
	 * @param resource
	 * @param target
	 * @param <T>
	 * @return
	 */
	public static <T> T beanCopy(Object resource,Class<T> target) {
		// 创建传入的字节码文件对应的对象
		T targetObj = null;
		try {
			targetObj = target.newInstance();
			BeanUtils.copyProperties(resource, targetObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetObj;
	}

	/**
	 * List集合的bean拷贝
	 * @param resource
	 * @param target
	 * @param <C>
	 * @return
	 */
	public static <C,T> List<C> beanCopyList(List<T> resource, Class<C> target) {
		return resource.stream()
				.map(obj -> beanCopy(obj,target))
				.collect(Collectors.toList());
	}

	// 测试工具类
	public static void main(String[] args) {
		List articles = new ArrayList<>();
		Article article = new Article();
		article.setId(4L);
		article.setTitle("钢铁是怎样炼成的");
		article.setViewCount(5000L);
		articles.add(article);

		List list = beanCopyList(articles, HotArticleVo.class);
		for (Object o : list) {
			System.out.println(o);
		}
	}
}
