package com.cms.controller.front;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.cms.Feedback;
import com.cms.entity.Member;
import com.cms.routes.RouteMapping;

/**
 * Controller - 首页
 * 
 * 
 * 
 */
@RouteMapping(url = "/")
public class IndexController extends BaseController {
	
	/**
	 * 首页
	 */
	public void index() {
		render("/templates/"+getTheme()+"/index.html");
	}
	
	   
	/**
     * 地图
     */
    public void map() {
        render("/templates/"+getTheme()+"/map.html");
    }
    
    /**
     * 登录
     */
    public void login() {
        render("/templates/"+getTheme()+"/login.html");
    }
    
    public void loginSubmit(){
        String zhanghao = getPara("zhanghao");
        String mima = getPara("mima");
        Member member = new Member().dao().findByUsername(zhanghao);
        if(member == null){
            renderJson(Feedback.error("用户不存在"));
        }else if(!DigestUtils.md5Hex(mima).equals(member.getPassword())){
            renderJson(Feedback.error("用户名密码错误"));
        }else{
            getSession().setAttribute(Member.SESSION_MEMBER, member);
            renderJson(Feedback.success(new HashMap<>()));
        }
    }
    
    /**
     * 注销
     */
    public void logout(){
        getSession().removeAttribute(Member.SESSION_MEMBER);
        redirect("/");
    }
    
    /**
     * 注册
     */
    public void register() {
        render("/templates/"+getTheme()+"/register.html");
    }
    
    public void registerSubmit(){
        String zhanghao = getPara("zhanghao");
        String mima = getPara("mima");
        String shoujihaoma = getPara("shoujihaoma");
        String dianziyouxiang = getPara("dianziyouxiang");
        String gongsimingchen = getPara("gongsimingchen");
        String cgylzl = getPara("cgylzl");
        Member member = new Member();
        member.setUsername(zhanghao);
        member.setPassword(DigestUtils.md5Hex(mima));
        member.setCreateDate(new Date());
        member.setModifyDate(new Date());
        member.setPhone(shoujihaoma);
        member.setEmail(dianziyouxiang);
        member.setCompanyName(gongsimingchen);
        member.setCgylzl(cgylzl);
        member.save();
        renderJson(Feedback.success(new HashMap<>()));
    }
}





