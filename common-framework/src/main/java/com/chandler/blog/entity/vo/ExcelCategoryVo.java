package com.chandler.blog.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelCategoryVo {
	@ExcelProperty("分类名")
	private String name;
	//描述
	@ExcelProperty("描述")
	private String description;

	//状态0:正常,1禁用
	@ExcelProperty("状态0:正常,1禁用")
	private String status;
}