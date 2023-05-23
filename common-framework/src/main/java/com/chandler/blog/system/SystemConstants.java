package com.chandler.blog.system;

public class SystemConstants
{
	/**
	 *  文章是草稿
	 */
	public static final int ARTICLE_STATUS_DRAFT = 1;
	/**
	 *  文章是正常发布状态
	 */
	public static final int ARTICLE_STATUS_NORMAL = 0;

	/**
	 * 文章是正常状态
	 */
	public static final String STATUS_NORMAL = "0";

	/**
	 * 文章是禁用状态
	 */
	public static final String STATUS_DISABLED = "1";

	/**
	 * 友链审核通过
	 */
	public static final String LINK_STATUS_PASS = "0";

	/**
	 * 友链未审核
	 */
	public static final String LINK_STATUS_UNREAD = "1";

	/**
	 * 友链审核未通过
	 */
	public static final String LINK_STATUS_FAILED = "2";

	/*
	 *	根评论id
	 */
	public static final int ROOR_ID = -1;

	/**
	 * 评论类型：文章类型
	 */
	public static final String ARTICLE_COMMENT = "0";

	/**
	 * 评论类型：友联类型
	 */
	public static final String LINK_COMMENT = "1";

	public static final String ARTICLE_VIEW_COUNT_KEY = "article:viewCount";

	/* 自增步长为1 */
	public static final Long INCR_STEP = 1L;

	/* 权限类型：菜单类型 */
	public static final Character MENU = 'C';

	/* 权限类型： */
	public static final Character BUTTON = 'F';

	/* 目录权限 */
	public static final Character CATALOGUE = 'M';

	/* 是否删除：是 */
	public static final String DEL_FLAG_TRUE = "1";

	/* 是否删除：否 */
	public static final String DEL_FLAG_FLASE = "0";
}
