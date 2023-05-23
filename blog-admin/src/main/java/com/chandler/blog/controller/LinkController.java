package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.*;
import com.chandler.blog.entity.vo.PageVo;
import com.chandler.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

	@Autowired
	private LinkService linkService;

	/**
	 * 分页查询分类列表
	 * @param pageNum
	 * @param pageSize
	 * @param name 根据友链名称进行模糊查询
	 * @param status 根据状态进行查询
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<PageVo> queryLinkList(Integer pageNum, Integer pageSize, String name, String status) {
		return linkService.linkService(pageNum, pageSize, name, status);
	}

	/**
	 * 新增友链功能
	 *
	 * @param adminAddLinkDto
	 * @return
	 */
	@PostMapping
	public ResponseResult addLink(@RequestBody AdminAddLinkDto adminAddLinkDto) {
		return linkService.addLink(adminAddLinkDto);
	}

	/**
	 * 回显友链信息，用于修改友链接口
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<AdminLinkVo> echoLinkById(@PathVariable("id") Long id) {
		return linkService.echoLinkById(id);
	}

	/**
	 * 修改友链接口
	 *
	 * @param adminUpdateLinkDto
	 * @return
	 */
	@PutMapping
	public ResponseResult updateLink(@RequestBody AdminUpdateLinkDto adminUpdateLinkDto) {
		return linkService.updateLink(adminUpdateLinkDto);
	}

	/**
	 * 删除某个友链（逻辑删除）
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult deleteLink(@PathVariable("id") Long id) {
		return linkService.deleteLink(id);
	}

	/**
	 * 改变友联审核状态
	 * @param linkStatusDto
	 * @return
	 */
	@PutMapping("/changeLinkStatus")
	public ResponseResult changeLinkStatus(@RequestBody LinkStatusDto linkStatusDto) {
		return linkService.changeLinkStatus(linkStatusDto);
	}
}
