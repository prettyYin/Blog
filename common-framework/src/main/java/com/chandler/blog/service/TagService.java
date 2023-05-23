package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Tag;
import com.chandler.blog.entity.dto.TagDto;
import com.chandler.blog.entity.vo.TagVo;

/**
 * 标签(Tag)表服务接口
 *
 * @author chandler
 */
public interface TagService extends IService<Tag> {

	ResponseResult<TagVo> queryTagList(Integer pageNum, Integer pageSize, String name, String remark);

	ResponseResult addTag(String token, TagDto tagDto);

	ResponseResult deleteTag(String token, Long id);

	ResponseResult<TagVo> echoTagById(String token, Long id);

	ResponseResult updateTag(String token, TagDto tagDto);

	ResponseResult<TagVo> listAllTag();
}
