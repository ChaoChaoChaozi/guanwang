/*
 * 
 * 
 * 
 */
package com.cms.controller.admin;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cms.CommonAttribute;
import com.cms.Feedback;
import com.cms.entity.Admin;
import com.cms.routes.RouteMapping;


/**
 * Controller - 管理员
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/admin")

public class AdminController extends BaseController {

	/**
	 * 检查用户名是否存在
	 */
	public void checkUsername() {
		String username = getPara("username");
		if (StringUtils.isEmpty(username)) {
			renderJson(false);
		}
		renderJson(!new Admin().dao().usernameExists(username));
	}

	/**
	 * 添加
	 */
	public void add() {
		render(getView("admin/add"));
	}

	/**
	 * 保存
	 */
	public void save() {
		Admin admin =  getModel(Admin.class,"",true); 
		if (new Admin().dao().usernameExists(admin.getUsername())) {
			render(CommonAttribute.ADMIN_ERROR_VIEW);
			return;
		}
		admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		admin.setLoginDate(null);
		admin.setLoginIp(null);
		admin.setCreateDate(new Date());
		admin.setModifyDate(new Date());
		admin.save();
		redirect(getListQuery("/admin/admin/list"));
	}

	/**
	 * 编辑
	 */
	public void edit() {
		Long id = getParaToLong("id");
		setAttr("admin", new Admin().dao().findById(id));
		render(getView("admin/edit"));
	}

	/**
	 * 更新
	 */
	public void update() {
		Admin admin =  getModel(Admin.class,"",true); 
		Admin pAdmin = new Admin().dao().findById(admin.getId());
		if (pAdmin == null) {
			render(CommonAttribute.ADMIN_ERROR_VIEW);
			return;
		}
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			admin.setPassword(DigestUtils.md5Hex(admin.getPassword()));
		} else {
			admin.setPassword(pAdmin.getPassword());
		}
		admin.setModifyDate(new Date());
		admin.update();
		redirect(getListQuery("/admin/admin/list"));
	}

	/**
	 * 列表
	 */
	public void list() {
	    String name = getPara("name");
	    String username = getPara("username");
		Integer pageNumber = getParaToInt("pageNumber");
		if(pageNumber==null){
			pageNumber = 1;
		}
		setAttr("page", new Admin().dao().findPage(name,username,pageNumber,PAGE_SIZE));
		setAttr("name", name);
		setAttr("username", username);
		render(getView("admin/list"));
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long ids[] = getParaValuesToLong("ids");
		if(ArrayUtils.isNotEmpty(ids)){
			for(Long id:ids){
				new Admin().dao().deleteById(id);
			}
		}
		renderJson(Feedback.success(new HashMap<>()));
	}

}