/*
 * 
 * 
 * 
 */
package com.cms.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.cms.Feedback;
import com.cms.entity.Category;
import com.cms.entity.Content;
import com.cms.routes.RouteMapping;


/**
 * Controller - 分类
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/category")

public class CategoryController extends BaseController {

	/**
	 * 添加
	 */
	public void add() {
		setAttr("categoryTree", new Category().dao().findTree());
		render(getView("category/add"));
	}
	
	/**
	 * 保存
	 */
	public void save() {
		Category category = getModel(Category.class,"",true); 
		if(category.getIsMenu()==null){
            category.setIsMenu(false);
        }
		category.setValue();
		category.setCreateDate(new Date());
		category.setModifyDate(new Date());
		category.save();
		redirect(getListQuery("/admin/category/list"));
	}

	/**
	 * 编辑
	 */
	public void edit() {
		Long id = getParaToLong("id");
		Category category = new Category().dao().findById(id);
		setAttr("categoryTree", new Category().dao().findTree());
		setAttr("category", category);
		render(getView("category/edit"));
	}

	/**
	 * 更新
	 */
	public void update() {
		Category category = getModel(Category.class,"",true); 
        if(category.getIsMenu()==null){
            category.setIsMenu(false);
        }
		category.setValue();
		category.setModifyDate(new Date());
		category.update();
		redirect(getListQuery("/admin/category/list"));
	}

	/**
	 * 列表
	 */
	public void list() {
		setAttr("categoryTree", new Category().dao().findTree());
		render(getView("category/list"));
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long id = getParaToLong("id");
		Category category = new Category().dao().findById(id);
		if (category == null) {
			renderJson(Feedback.error("分类不存在"));
			return;
		}
		List<Category> children = category.getChildren();
		if (children != null && !children.isEmpty()) {
			renderJson(Feedback.error("存在下级分类，无法删除"));
			return;
		}
		List<Content> articles = category.getContents();
		if (articles != null && !articles.isEmpty()) {
			renderJson(Feedback.error("存在下级内容，无法删除"));
			return;
		}
		new Category().dao().deleteById(id);
		renderJson(Feedback.success(new HashMap<>()));
	}

}