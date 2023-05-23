package com.chandler.blog.entity;


import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author chandler
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role_menu")
public class RoleMenu  implements Serializable {
    //角色ID
//    @TableId
    @MppMultiId // 复合主键
    @TableField(value = "role_id")
    private Long roleId;
    //菜单ID
//    @TableId
    @MppMultiId // 复合主键
    @TableField(value = "menu_id")
    private Long menuId;

}
