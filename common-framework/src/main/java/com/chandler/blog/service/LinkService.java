package com.chandler.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.Link;
import com.chandler.blog.entity.dto.AdminAddLinkDto;
import com.chandler.blog.entity.dto.AdminLinkVo;
import com.chandler.blog.entity.dto.AdminUpdateLinkDto;
import com.chandler.blog.entity.dto.LinkStatusDto;
import com.chandler.blog.entity.vo.PageVo;

/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 */
public interface LinkService extends IService<Link> {

	ResponseResult getAllLink();

	ResponseResult<PageVo> linkService(Integer pageNum, Integer pageSize, String name, String status);

	ResponseResult addLink(AdminAddLinkDto adminAddLinkDto);

	ResponseResult<AdminLinkVo> echoLinkById(Long id);

	ResponseResult updateLink(AdminUpdateLinkDto adminUpdateLinkDto);

	ResponseResult deleteLink(Long id);

	ResponseResult changeLinkStatus(LinkStatusDto linkStatusDto);
}

