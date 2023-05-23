package com.chandler.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Category;
import com.chandler.blog.entity.Link;
import com.chandler.blog.entity.dto.AdminAddLinkDto;
import com.chandler.blog.entity.dto.AdminLinkVo;
import com.chandler.blog.entity.dto.AdminUpdateLinkDto;
import com.chandler.blog.entity.dto.LinkStatusDto;
import com.chandler.blog.entity.vo.AdminCategoryVo;
import com.chandler.blog.entity.vo.LinkVo;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.mapper.LinkMapper;
import com.chandler.blog.service.LinkService;
import com.chandler.blog.system.SystemConstants;
import com.chandler.blog.util.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(SgLink)表服务实现类
 *
 * @author makejava
 */
@Service("sgLinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

	/**
	 * 查询所有友链信息
	 *
	 * @return
	 */
	@Override
	public ResponseResult getAllLink() {
		// 查询所有状态正常的友链信息
		LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_PASS);
		List<Link> links = list(queryWrapper);
		// 封装成Vo对象
		List<LinkVo> linkVos = BeanCopyUtils.beanCopyList(links, LinkVo.class);
		// 封装并返回Json数据
		return ResponseResult.okResult(linkVos);
	}

	@Override
	public ResponseResult<PageVo> linkService(Integer pageNum, Integer pageSize, String name, String status) {
		LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.hasText(name), Link::getName, name);
		wrapper.eq(StringUtils.hasText(status), Link::getStatus, status);
		Page<Link> page = new Page<>(pageNum, pageSize);
		page(page, wrapper);
		List<Link> categories = page.getRecords();
		List<AdminLinkVo> adminLinkVos = BeanCopyUtils.beanCopyList(categories, AdminLinkVo.class);
		PageVo pageVo = new PageVo(adminLinkVos, page.getTotal());
		return ResponseResult.okResult(pageVo);
	}

	@Override
	public ResponseResult addLink(AdminAddLinkDto adminAddLinkDto) {
		Link link = BeanCopyUtils.beanCopy(adminAddLinkDto, Link.class);
		save(link);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult<AdminLinkVo> echoLinkById(Long id) {
		Link link = getBaseMapper().selectById(id);
		AdminLinkVo adminLinkVo = BeanCopyUtils.beanCopy(link, AdminLinkVo.class);
		return ResponseResult.okResult(adminLinkVo);
	}

	@Override
	public ResponseResult updateLink(AdminUpdateLinkDto adminUpdateLinkDto) {
		Link link = BeanCopyUtils.beanCopy(adminUpdateLinkDto, Link.class);
		updateById(link);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult deleteLink(Long id) {
		LambdaUpdateWrapper<Link> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Link::getId, id)
				.set(Link::getDelFlag, SystemConstants.DEL_FLAG_TRUE);
		update(null, wrapper);
		return ResponseResult.okResult();
	}

	@Override
	public ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto) {
		LambdaUpdateWrapper<Link> wrapper = new LambdaUpdateWrapper<>();
		wrapper.eq(Link::getId, linkStatusDto.getId())
				.set(Link::getStatus, linkStatusDto.getStatus());
		update(null, wrapper);
		return ResponseResult.okResult();
	}
}

