package com.cms.entity;

import com.cms.entity.base.BaseGb;
import com.cms.util.DBUtils;
import com.jfinal.plugin.activerecord.Page;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Gb extends BaseGb<Gb> {
    
    /**
     * 
     * @param pageNumber
     *            页码
     * @param pageSize
     *            每页记录数
     */
    public Page<Gb> findPage(Integer pageNumber,Integer pageSize){
        String orderBySql = DBUtils.getOrderBySql("createDate desc");
        return paginate(pageNumber, pageSize, "select *", "from kf_gb where 1=1 "+orderBySql);
    }
}