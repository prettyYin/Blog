package com.chandler.blog;

public enum AppHttpCodeEnum {
	// 成功
	SUCCESS(200, "操作成功"),
	// 登录
	NEED_LOGIN(401, "需要登录后操作"),
	NO_OPERATOR_AUTH(403, "无权限操作"),
	SYSTEM_ERROR(500, "出现错误"),
	USERNAME_EXIST(501, "用户名已存在"),
	PHONENUMBER_EXIST(502, "手机号已存在"),
	EMAIL_EXIST(503, "邮箱已存在"),
	REQUIRE_USERNAME(504, "必需填写用户名"),
	LOGIN_ERROR(505, "用户名或密码错误"),
	CONTENT_NOT_NULL(506, "文章内容不能为空"),
	FILE_TYPE_ERROR(507, "文件类型错误"),
	REQUIRE_NICKNAME(508, "必需填写昵称"),
	REQUIRE_PASSWORD(509, "必需填写密码"),
	REQUIRE_EMAIL(510, "必需填写邮箱"),
	NICKNAME_EXIST(511, "昵称已存在"),
	TAG_NAME_NOT_NULL(512, "标签名不能为空"),
	TITLE_NOT_NULL(513, "文章标题不能为空"),
	SUMMARY_NOT_NULL(514, "文章摘要不能为空"),
	THUMBNAIL_NOT_NULL(515, "缩略图不能为空"),
	FILE_DOWNLOAD_ERROR(516, "文件下载失败"),
	NO_CATEGORIES(517,"没有分类数据") ;
	int code;
	String msg;

	AppHttpCodeEnum(int code, String errorMessage) {
		this.code = code;
		this.msg = errorMessage;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
