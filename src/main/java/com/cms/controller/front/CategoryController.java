package com.cms.controller.front;

import com.cms.entity.Category;
import com.cms.entity.Content;
import com.cms.routes.RouteMapping;

/**
 * Controller - 栏目
 * 
 * 
 * 
 */
@RouteMapping(url = "/category")
public class CategoryController extends BaseController {

	/**
	 * 栏目
	 */
	public void index() {
		Long categoryId = getParaToLong(0);
		Category category = new Category().dao().findById(categoryId);
		setAttr("currentCategory", category);
		Integer pageNumber = getParaToInt("pageNumber");
		if(pageNumber==null){
			pageNumber = 1;
		}
		int pageSize = 20 ; 
		String templateName = "";
		if(category.getType()==Category.XW_TYPE){
            templateName = "newsList.html";
        }else if(category.getType()==Category.CP_TYPE){
            templateName = "productList.html";
        }else if(category.getType()==Category.YM_TYPE){
            templateName = category.getPageTemplate();
        }
		setAttr("page", new Content().dao().findPage(categoryId,null,pageNumber,pageSize));
		render("/templates/"+getTheme()+"/"+templateName);
	}
}
