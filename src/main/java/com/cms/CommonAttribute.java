/*
 * 
 * 
 * 
 */
package com.cms;

/**
 * 公共参数
 * 
 * 
 * 
 */
public final class CommonAttribute {
	
	/** 日期格式配比 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/** UTF-8编码 */
	public static final String UTF_8 = "UTF-8";
	
	/** POST */
	public static final String POST="post";
	
	/** GET */
	public static final String GET="get";
	
	/** 后台页面 */
	public static final String ADMIN_PATH="/WEB-INF/admin/";
	
	/** 后台错误页面 */
	public static final String ADMIN_ERROR_VIEW = ADMIN_PATH+"error/500.html";
	
	/** 后台权限错误页面 */
	public static final String ADMIN_UNAUTHORIZED_VIEW = ADMIN_PATH+"error/403.html";
	
	/** 前台错误页面 */
	public static final String FRONT_ERROR_VIEW = "/500.html";
	
	/** 前台权限错误页面 */
	public static final String FRONT_RESOURCE_NOT_FOUND_VIEW = "/404.html";
	
	/** config.xml文件路径 */
	public static final String CONFIG_XML_PATH = "/config.xml";
	
	/** config.properties */
	public static final String CONFIG_PROPERTIES = "config.properties";
	
	/** job.properties */
	public static final String JOB_PROPERTIES = "job.properties";
	
	/** 页面后缀 */
	public static final String VIEW_EXTENSION = ".html";
	
	/** 参数分隔符 */
	public static final String URL_PARA_SEPARATOR = "-";
	
	/** 上传文件目录 */
	public static final String BASE_UPLOAD_PATH = "upload";
	
	/** JSON时间格式 */
	public static final String JSON_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 不可实例化
	 */
	private CommonAttribute() {
	}
}