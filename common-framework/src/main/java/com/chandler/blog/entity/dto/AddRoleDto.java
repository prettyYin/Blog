package com.chandler.blog.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDto {

	//角色名称
	private String roleName;
	//角色权限字符串
	private String roleKey;
	//显示顺序
	private Integer roleSort;
	//角色状态（0正常 1停用）
	private String status;
	//备注
	private String remark;
	// 菜单id
	private List<Long> menuIds;

}
