package com.cms.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import com.alibaba.fastjson.annotation.JSONField;
import com.cms.entity.base.BaseCategory;
import com.cms.util.DBUtils;
import com.cms.util.SystemUtils;

/**
 * Entity - 分类
 * 
 * 
 * 
 */
@SuppressWarnings("serial")
public class Category extends BaseCategory<Category> {
	
	/** 树路径分隔符 */
	public static final String TREE_PATH_SEPARATOR = ",";
	
	//新闻
	public static final Integer XW_TYPE=1;
	//产品
    public static final Integer CP_TYPE=2;
    //页面
    public static final Integer YM_TYPE=3;
	
	/**
	 * 内容
	 */
	@JSONField(serialize=false)  
	private List<Content> contents;
	
	/**
	 * 下级分类
	 */
	@JSONField(serialize=false)  
	private List<Category> children;
	
	/**
	 * 上级分类
	 */
	@JSONField(serialize=false)  
	private Category parent;
	
	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<Content> getContents(){
	    if(contents == null){
	        contents = new Content().dao().find("select * from kf_content where categoryId = ?",getId());
	    }
		return contents;
	}
	
	/**
	 * 获取下级分类
	 * 
	 * @return 下级分类
	 */
	public List<Category> getChildren() {
	    if(children == null){
	        children = find("select * from kf_category where parentId=?",getParentId());
	    }
		return children;
	}
	
	/**
	 * 获取上级分类
	 * @return	上级分类
	 */
	public Category getParent(){
	    if(parent == null){
	        parent = findById(getParentId());
	    }
		return parent;
	}
	
	
	/**
	 * 查找顶级分类
	 * 
	 * @param isMenu
	 *            是否是菜单
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 顶级分类
	 */
	public List<Category> findRoots(Boolean isMenu,Integer start,Integer count){
	    String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		String countSql=DBUtils.getCountSql(start, count);
		String orderBySql = DBUtils.getOrderBySql("sort asc");
		return find("select * from kf_category where parentId is null"+filterSql+orderBySql+countSql);
	}
	
	
	/**
	 * 查找上级分类
	 * 
	 * @param categoryId
	 *            分类Id
	 * @param isMenu
	 *            是否是菜单
	 * @param recursive
	 *            是否递归
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 上级分类
	 */
	public List<Category> findParents(Long categoryId,Boolean isMenu,Boolean recursive,Integer start, Integer count){
		Category category = findById(categoryId);
		if(categoryId == null || category.getParentId() == null){
			return Collections.emptyList();
		}
		String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		if(recursive){
			String countSql=DBUtils.getCountSql(start, count);
			String orderBySql = DBUtils.getOrderBySql("grade asc");
			return find("select * from kf_category where id in ("+StringUtils.join(category.getParentIds(), ",")+") "+filterSql+orderBySql+countSql);
		}else{
			return find("select * from kf_category where id = ? "+filterSql,findById(categoryId).getParentId());
		}
	}
	
	
	/**
	 * 查找下级分类
	 * 
	 * @param categoryId
	 *            分类Id
	 * @param isMenu
	 *            是否是菜单
	 * @param recursive
	 *            是否递归
	 * @param start
     *            起始位置
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	public List<Category> findChildren(Long categoryId,Boolean isMenu,Boolean recursive,Integer start,Integer count){
	    String filterSql = "";
		if(isMenu!=null){
		    filterSql+= " and isMenu="+BooleanUtils.toInteger(isMenu);
		}
		if(recursive){
			String countSql=DBUtils.getCountSql(start, count);
			String orderBySql = DBUtils.getOrderBySql("grade asc,sort asc");
			List<Category> categories;
			if(categoryId!=null){
				categories = find("select * from kf_category where 1=1 and treePath like ? "+filterSql+orderBySql+countSql,"%,"+categoryId+",%");
			}else{
				categories = find("select * from kf_category where 1=1 "+filterSql+orderBySql+countSql);
			}
			sort(categories);
			return categories;
		}else{
		    String orderBySql = DBUtils.getOrderBySql("sort asc");
			return find("select * from kf_category where parentId = ? "+filterSql+orderBySql,categoryId);
		}
	}
	
	/**
	 * 查找分类树
	 * 
	 * @return 分类树
	 */
	public List<Category> findTree(){
		return findChildren(null,null,true,null,null);
	}
	
	
	/**
	 * 获取所有上级分类ID
	 * 
	 * @return 所有上级分类ID
	 */
	public Long[] getParentIds() {
		String[] treePaths = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		Long[] result = new Long[treePaths.length];
		for (int i = 0; i < treePaths.length; i++) {
			result[i] = Long.valueOf(treePaths[i]);
		}
		return result;
	}
	
	/**
	 * 排序分类
	 * 
	 * @param categories
	 *            分类
	 */
	private void sort(List<Category> categories) {
		if(categories == null || categories.size()==0) {
			return;
		}
		final Map<Long, Integer> sortMap = new HashMap<Long, Integer>();
		for (Category category : categories) {
		    sortMap.put(category.getId(), category.getSort());
		}
		Collections.sort(categories, new Comparator<Category>() {
			@Override
			public int compare(Category category1, Category category2) {
				Long[] ids1 = (Long[]) ArrayUtils.add(category1.getParentIds(), category1.getId());
				Long[] ids2 = (Long[]) ArrayUtils.add(category2.getParentIds(), category2.getId());
				Iterator<Long> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<Long> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					Long id1 = iterator1.next();
					Long id2 = iterator2.next();
					Integer sort1 = sortMap.get(id1);
					Integer sort2 = sortMap.get(id2);
					compareToBuilder.append(sort1, sort2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(category1.getGrade(), category2.getGrade());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}
	
	
	/**
	 * 设置值
	 * 
	 */
	public void setValue() {
		if (getParentId() != null) {
			setTreePath(getParent().getTreePath() + getParent().getId() + Category.TREE_PATH_SEPARATOR);
		} else {
			setTreePath(Category.TREE_PATH_SEPARATOR);
		}
		setGrade(getParentIds().length);
	}
	
    /**
     * 获取路径
     * 
     * @return 路径
     */
    public String getPath() {
        return getListPath(1);
    }
    
    /**
     * 获取动态路径
     * 
     * @return 动态路径
     */
    public String getDynamicPath(){
        return "/category/"+getId();
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
     * 获取分页路径
     * 
     * @return 分页路径
     */
    public String getListPath(Integer pageNumber) {
        if(pageNumber == 1){
            return getDynamicPath();
        }
        return getDynamicListPath(pageNumber);
    }
    
    /**
     * 获取动态分页路径
     * 
     * @return 动态分页路径
     */
    public String getDynamicListPath(Integer pageNumber){
        return "/category/"+getId()+"?pageNumber="+pageNumber;
    }
}
