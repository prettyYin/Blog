package com.chandler.blog.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author chandler
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole  {
    //用户ID
//    @TableId
    @MppMultiId // 复合主键
    @TableField(value = "user_id")
    private Long userId;
    //角色ID
//    @TableId
    @MppMultiId // 复合主键
    @TableField(value = "role_id")
    private Long roleId;




}
