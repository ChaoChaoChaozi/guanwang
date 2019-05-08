/*
 * 
 * 
 * 
 */
package com.cms.controller.front;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.cms.Feedback;
import com.cms.entity.Category;
import com.cms.entity.Content;
import com.cms.entity.Member;
import com.cms.routes.RouteMapping;
import com.cms.util.SystemUtils;
import com.jfinal.kit.PathKit;

/**
 * Controller - 内容
 * 
 * 
 * 
 */
@RouteMapping(url = "/content")
public class ContentController extends BaseController {

	/**
	 * 内容
	 */
	public void index() {
        Long contentId = getParaToLong(0);
        Content content = new Content().dao().findById(contentId);
        setAttr("currentContent", content);
        Category category = content.getCategory();
        String templateName = "";
        if(category.getType()==Category.XW_TYPE){
            templateName = "newsContent.html";
        }else if(category.getType()==Category.CP_TYPE){
            templateName = "productContent.html";
        }
        render("/templates/"+getTheme()+"/"+templateName);
	}
	
    public void detail(){
        Long contentId = getParaToLong("id");
        Content content = new Content().dao().findById(contentId);
        Map<String,Object> result = new HashMap<>();
        result.put("title", content.getTitle());
        result.put("source", content.getSource());
        result.put("description", content.getDescription());
        result.put("author", content.getAuthor());
        result.put("image", content.getImage());
        result.put("tupianji", content.getTupianjis());
        renderJson(Feedback.success(result));

	}
    
    public void wlsx(){
        Member member = getCurrentMember();
        if(member == null){
            redirect("/login");
            return;
        }
        Long contentId = getParaToLong("id");
        Content content = new Content().dao().findById(contentId);
        String wlsx = content.getWlsx();
        String siteUrl = SystemUtils.getConfig().getSiteUrl();
        File file = new File(PathKit.getWebRootPath()+wlsx.replace(siteUrl, ""));
        renderFile(file);
    }
    
    public void sgsrz(){
        Member member = getCurrentMember();
        if(member == null){
            redirect("/login");
            return;
        }
        Long contentId = getParaToLong("id");
        Content content = new Content().dao().findById(contentId);
        String sgsrz = content.getSgsrz();
        String siteUrl = SystemUtils.getConfig().getSiteUrl();
        File file = new File(PathKit.getWebRootPath()+sgsrz.replace(siteUrl, ""));
        renderFile(file);
    }
    
    public void ulrz(){
        Member member = getCurrentMember();
        if(member == null){
            redirect("/login");
            return;
        }
        Long contentId = getParaToLong("id");
        Content content = new Content().dao().findById(contentId);
        String ulrz = content.getUlrz();
        String siteUrl = SystemUtils.getConfig().getSiteUrl();
        File file = new File(PathKit.getWebRootPath()+ulrz.replace(siteUrl, ""));
        renderFile(file);
    }
    
    public void msdsrz(){
        Member member = getCurrentMember();
        if(member == null){
            redirect("/login");
            return;
        }
        Long contentId = getParaToLong("id");
        Content content = new Content().dao().findById(contentId);
        String msdsrz = content.getMsdsrz();
        String siteUrl = SystemUtils.getConfig().getSiteUrl();
        File file = new File(PathKit.getWebRootPath()+msdsrz.replace(siteUrl, ""));
        renderFile(file);
    }
	
   /**
     * 搜索
     */
    public void search() {
        String keyword = getPara("keyword");
        Integer pageNumber = getParaToInt("pageNumber");
        if(pageNumber==null){
            pageNumber = 1;
        }
        int pageSize = 20 ; 
        setAttr("page", new Content().dao().search(keyword,pageNumber,pageSize));
        setAttr("keyword", keyword);
        render("/templates/"+getTheme()+"/search.html");
    }

	/**
	 * 点击数
	 */
	public void hits() {
		Long id = getParaToLong(0);
		if (id == null) {
			renderJson(0L);
			return;
		}
		Content content = new Content().dao().findById(id);
		Long hits = content.getHits();
		hits = hits+1;
		content.setHits(hits);
		content.update();
		renderJavascript("document.write("+hits+")");
	}

}