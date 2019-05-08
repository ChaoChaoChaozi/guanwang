package com.cms;

import java.io.Serializable;

public class Config implements Serializable {

	private static final long serialVersionUID = 1012463140749529805L;

	/** 缓存名称 */
	public static final String CACHE_NAME = "config";

	/** 网站名称 */
	private String siteName;
	
	/** 网站网址 */
	private String siteUrl;
	
	/** logo */
	private String logo;
	
	/** 标题 */
	private String title;
	
	/** 关键词 */
	private String keywords;
	
	/** 描述 */
	private String description;
	
	/** 主题 */
	private String theme;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
