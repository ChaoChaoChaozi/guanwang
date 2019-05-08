package com.cms.controller.admin;

import java.util.HashMap;

import com.cms.Feedback;
import com.cms.entity.Member;
import com.cms.routes.RouteMapping;


/**
 * Controller - 会员
 * 
 * 
 * 
 */
@RouteMapping(url = "/admin/member")

public class MemberController extends BaseController{

	/**
	 * 查看
	 */
	public void view(){
		Long id = getParaToLong("id");
		setAttr("member", new Member().dao().findById(id));
		render(getView("member/view"));
	}
	
	/**
	 * 列表
	 */
	public void list(){
	    String username = getPara("username");
	    String phone = getPara("phone");
	    String email = getPara("email");
	    Integer pageNumber = getParaToInt("pageNumber");
        if(pageNumber==null){
            pageNumber = 1;
        }
		setAttr("page", new Member().dao().findPage(username,phone,email,pageNumber,PAGE_SIZE));
		setAttr("username", username);
		setAttr("phone", phone);
		setAttr("email", email);
		render(getView("member/list"));
	}
	
	/**
	 * 删除
	 */
	public void delete(){
		Long ids[] = getParaValuesToLong("ids");
		for(Long id:ids){
			new Member().dao().deleteById(id);
		}
		renderJson(Feedback.success(new HashMap<>()));
	}
}
