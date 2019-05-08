package com.cms.entity;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.cms.entity.base.BaseContent;
import com.cms.util.DBUtils;
import com.cms.util.SystemUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * Entity - 内容
 * 
 * 
 * 
 */
@SuppressWarnings("serial")
public class Content extends BaseContent<Content> {
	
    /**
     * 分类
     */
    @JSONField(serialize=false)  
    private Category category;
    
    /**
     * 上一篇内容
     */
    @JSONField(serialize=false)  
    private Content lastContent;
    
    /**
     * 下一篇内容
     */
    @JSONField(serialize=false)  
    private Content nextContent;
    
    /**
     * 查找内容分页
     * 
     * @param categoryId
     *            分类ID
     * @param pageNumber
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 内容分页
     */
    public Page<Content> findPage(Long categoryId,String title,Integer pageNumber,Integer pageSize){
        String filterSql = "";
        if(categoryId!=null){
            filterSql+=" and (categoryId="+categoryId+" or categoryId in ( select id from kf_category where treePath  like '%"+Category.TREE_PATH_SEPARATOR+categoryId+Category.TREE_PATH_SEPARATOR+"%'))";
        }
        if(StringUtils.isNotBlank(title)){
            filterSql+= " and title like '%"+title+"%'";
        }
        String orderBySql = DBUtils.getOrderBySql("createDate desc");
        return paginate(pageNumber, pageSize, "select *", "from kf_content where 1=1 "+filterSql+orderBySql);
    }
    
    /**
     * 搜索内容分页
     * 
     * @param keyword
     *            关键词
     * @param pageNumber
     *            页码
     * @param pageSize
     *            每页记录数
     * @return 内容分页
     */
    public Page<Content> search(String keyword,Integer pageNumber,Integer pageSize) {
        String filterSql = "";
        if(StringUtils.isNotBlank(keyword)){
            filterSql+= " and title like '%"+keyword+"%'";
        }
        String orderBySql = DBUtils.getOrderBySql("createDate desc");
        return paginate(pageNumber, pageSize, "select *", "from kf_content where 1=1 "+filterSql+orderBySql);
    }
    
    /**
     * 查找内容
     * 
     * @param categoryId
     *            内容分类ID
     * @param orderBy
     *            排序
     * @param start
     *            起始位置
     * @param count
     *            数量
     * @return 内容
     */
    public List<Content> findList(Long categoryId,String orderBy,Integer start,Integer count){
        String filterSql = "";
        if(categoryId!=null){
            filterSql+=" and (categoryId="+categoryId+" or categoryId in ( select id from kf_category where treePath  like '%"+Category.TREE_PATH_SEPARATOR+categoryId+Category.TREE_PATH_SEPARATOR+"%'))";
        }
        String orderBySql = "";
        if(StringUtils.isNotBlank(orderBy)){
            orderBySql = DBUtils.getOrderBySql(orderBy);
        }else{
            orderBySql = DBUtils.getOrderBySql("createDate desc");
        }
        String countSql=DBUtils.getCountSql(start, count);
        return find("select * from kf_content where 1=1 "+filterSql+orderBySql+countSql);
    }
	
	
	/**
	 * 获取分类
	 * @return 分类
	 */
	public Category getCategory(){
	    if(category == null){
	        category = new Category().dao().findById(getCategoryId());
	    }
		return category;
	}
	
    /**
     * 获取路径
     * 
     * @return 路径
     */
    public String getPath() {
        return getDynamicPath();
    }
    
    /**
     * 获取动态路径
     * 
     * @return 动态路径
     */
    public String getDynamicPath(){
        return "/content/"+getId();
    }
    
    /**
     * 获取URL
     * 
     * @return URL
     */
    public String getUrl() {
        return SystemUtils.getConfig().getSiteUrl() + getPath();
    }
    
    /**
     * 获取文本内容
     * 
     * @return 文本内容
     */
    public String getText() {
        if (StringUtils.isEmpty(getIntroduction())) {
            return StringUtils.EMPTY;
        }
        return Jsoup.parse(getIntroduction()).text();
    }
    
    public List<String> getTupianjis(){
        return JSONArray.parseArray(getTupianji(), String.class);
    }
}
