package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.AppHttpCodeEnum;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Tag;
import com.chandler.blog.entity.dto.TagDto;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.entity.vo.TagVo;
import com.chandler.blog.handler.exception.SystemException;
import com.chandler.blog.mapper.TagMapper;
import com.chandler.blog.service.TagService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author chandler
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

	@Override
	public ResponseResult<TagVo> queryTagList(Integer pageNum, Integer pageSize, String name, String remark) {
		LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(name), Tag::getName, name);
		wrapper.like(StringUtils.hasText(remark), Tag::getRemark, remark);
		Page<Tag> page = new Page<>(pageNum, pageSize);
		page(page, wrapper);
		List<Tag> tags = page.getRecords();
		List<TagVo> tagVos = BeanCopyUtils.beanCopyList(tags, TagVo.class);
		PageVo pageVo = new PageVo(tagVos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Override
	public ResponseResult addTag(String token, TagDto tagDto) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		String tagName = tagDto.getName();
		if (!StringUtils.hasText(tagName))
			throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
		Tag saveTag = BeanCopyUtils.beanCopy(tagDto, Tag.class);
		save(saveTag);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteTag(String token, Long id) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		LambdaUpdateWrapper<Tag> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Tag::getId, id);
		wrapper.set(Tag::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		getBaseMapper().update(null, wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<TagVo> echoTagById(String token, Long id) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(Tag::getId, id);
		Tag tag = getOne(wrapper);
		TagVo tagVo = BeanCopyUtils.beanCopy(tag, TagVo.class);
		return ResponseResult.okResult(tagVo);
	}

	@Override
	public ResponseResult updateTag(String token, TagDto tagDto) {
		if (!StringUtils.hasText(token)) {
			throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
		}
		if (!StringUtils.hasText(tagDto.getName()))
			throw new SystemException(AppHttpCodeEnum.TAG_NAME_NOT_NULL);
		Tag tag = BeanCopyUtils.beanCopy(tagDto, Tag.class);
		updateById(tag);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<TagVo> listAllTag() {
		LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(Tag::getId, Tag::getName);//只返回id和name
		List<Tag> tags = list(wrapper);
		List<TagVo> tagVos = BeanCopyUtils.beanCopyList(tags, TagVo.class);
		return ResponseResult.okResult(tagVos);
	}
}


