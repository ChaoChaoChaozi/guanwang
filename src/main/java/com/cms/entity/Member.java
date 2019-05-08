package com.cms.entity;

import org.apache.commons.lang.StringUtils;

import com.cms.entity.base.BaseMember;
import com.cms.util.DBUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * Entity -会员
 * 
 * 
 * 
 */
@SuppressWarnings("serial")
public class Member extends BaseMember<Member> {
    
    /**
     * 会员session名称
     */
    public static final String SESSION_MEMBER="session_member";
    
    
   /**
     * 查找会员分页
     * 
     * @param pageable
     *            分页信息
     * @return 会员分页
     */
    public Page<Member> findPage(String username,String phone,String email,Integer pageNumber,Integer pageSize){
        String filterSql = "";
        if(StringUtils.isNotBlank(username)){
            filterSql+= " and username like '%"+username+"%'";
        }
        if(StringUtils.isNotBlank(phone)){
            filterSql+= " and phone like '%"+phone+"%'";
        }
        if(StringUtils.isNotBlank(email)){
            filterSql+= " and email like '%"+email+"%'";
        }
        String orderBySql = DBUtils.getOrderBySql("createDate desc");
        return paginate(pageNumber, pageSize, "select *", "from kf_member where 1=1 "+filterSql+orderBySql);
    }
    
    public Member findByUsername(String username){
        return findFirst("select * from kf_member where username=? ", username);
    }
    
}
