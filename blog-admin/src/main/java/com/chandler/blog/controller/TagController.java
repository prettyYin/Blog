package com.chandler.blog.controller;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.entity.dto.TagDto;
import com.chandler.blog.entity.vo.TagVo;
import com.chandler.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

	@Autowired
	TagService tagService;

	/**
	 * 查询标签功能，要求能根据标签名进行分页查询。 (后期可能会增加备注查询等需求)
	 *
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param remark
	 * @return
	 */
	@GetMapping("/list")
	public ResponseResult<TagVo> queryTagList(Integer pageNum, Integer pageSize, String name, String remark) {
		return tagService.queryTagList(pageNum, pageSize, name, remark);
	}

	/**
	 * 新增标签
	 *
	 * @param token
	 * @return
	 */
	@PostMapping
	public ResponseResult addTag(@RequestHeader(value = "token") String token,
								 @RequestBody TagDto tagDto) {
		return tagService.addTag(token, tagDto);
	}

	/**
	 * 逻辑删除标签
	 *
	 * @param token
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseResult deleteTag(@RequestHeader(value = "token") String token,
									@PathVariable("id") Long id) {
		return tagService.deleteTag(token, id);
	}

	/**
	 * 获取标签信息，用于回显
	 *
	 * @param token
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseResult<TagVo> echoTagById(@RequestHeader(value = "token") String token,
											 @PathVariable("id") Long id) {
		return tagService.echoTagById(token, id);
	}

	/**
	 * 修改标签接口
	 *
	 * @param token
	 * @return
	 */
	@PutMapping
	public ResponseResult updateTag(@RequestHeader(value = "token") String token,
									@RequestBody TagDto tagDto) {
		return tagService.updateTag(token, tagDto);
	}
}
