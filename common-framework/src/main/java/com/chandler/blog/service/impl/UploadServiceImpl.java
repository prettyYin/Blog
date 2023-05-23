package com.chandler.blog.service.impl;

import com.chandler.blog.ResponseResult;
import com.chandler.blog.service.UploadService;
import com.chandler.blog.util.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@Setter
//@ConfigurationProperties(prefix = "oss")
@PropertySource("classpath:application.yml")
public class UploadServiceImpl implements UploadService {

	//...生成上传凭证，然后准备上传
	@Value(value = "${oss.accessKey}")
	private String accessKey;
	@Value(value = "${oss.secretKey}")
	private String secretKey;
	@Value(value = "${oss.bucket}")
	private String bucket;
	@Value(value = "${oss.imageHref}")
	private String imageHref;

	@Override
	@Transactional
	public ResponseResult uploadAvatar(MultipartFile img) {
		String originalFilename = img.getOriginalFilename();
		String filePath = PathUtils.generateFilePath(originalFilename,"avatars");
		String url = uploadToOSS(img, filePath);
		return ResponseResult.okResult(url);
	}

	@Override
	@Transactional
	public ResponseResult uploadImg(MultipartFile img) {
		String originalFilename = img.getOriginalFilename();
		String filePath = PathUtils.generateFilePath(originalFilename,"blog_images");
		String url = uploadToOSS(img, filePath);
		return ResponseResult.okResult(url);
	}

	private String uploadToOSS(MultipartFile imgFile,String filePath) {
		//构造一个带指定 Region 对象的配置类
		Configuration cfg = new Configuration(Region.autoRegion());
		cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
		//...其他参数参考类注释

		UploadManager uploadManager = new UploadManager(cfg);

		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = filePath;
		try {
			InputStream fileInputStream = imgFile.getInputStream();
			Auth auth = Auth.create(accessKey, secretKey);
			String upToken = auth.uploadToken(bucket);

			try {
				Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
				//解析上传成功的结果
				DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//				System.out.println(putRet.key); // key即是文件名
//				System.out.println(putRet.hash);
				return imageHref + key; // 返回图片的外链地址
			} catch (QiniuException ex) {
				Response r = ex.response;
				System.err.println(r.toString());
				try {
					System.err.println(r.bodyString());
				} catch (QiniuException ex2) {
					//ignore
				}
			}
		} catch (Exception ex) {
			//ignore
		}
		return "upload error";
	}
}
