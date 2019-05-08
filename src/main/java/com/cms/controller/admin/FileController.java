/*
 * 
 * 
 * 
 */
package com.cms.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;

import com.cms.CommonAttribute;
import com.cms.routes.RouteMapping;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

/**
 * Controller - 文件
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/file")

public class FileController extends BaseController {

	/**
	 * 上传
	 */
	public void upload() {
		UploadFile uploadFile = getFile();
		Map<String, Object> data = new HashMap<String, Object>();
		if (uploadFile == null || uploadFile.getFile().length()==0) {
			data.put("message", "操作错误");
			data.put("state", "ERROR");
			renderJson(data);
			return;
		}
		String newFileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(uploadFile.getOriginalFileName());
		String url = "/"+CommonAttribute.BASE_UPLOAD_PATH+"/"+newFileName;
		uploadFile.getFile().renameTo(new File(PathKit.getWebRootPath()+url));
		data.put("message", "成功");
		data.put("state", "SUCCESS");
		data.put("url", url);
		data.put("name",FilenameUtils.getBaseName(url));
		uploadFile.getFile().delete();
		renderJson(data);
	}
}