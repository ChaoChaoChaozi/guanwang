package com.cms.controller.admin;

import org.apache.commons.lang.ArrayUtils;

import com.cms.entity.Gb;
import com.cms.routes.RouteMapping;

@RouteMapping(url = "/admin/gb")
public class GbController extends BaseController {

    /**
     * 查看
     */
    public void view() {
        Long id = getParaToLong("id");
        setAttr("gb", new Gb().dao().findById(id));
        render(getView("gb/view"));
    }
    
    /**
     * 列表
     */
    public void list() {
        Integer pageNumber = getParaToInt("pageNumber");
        if(pageNumber==null){
            pageNumber = 1;
        }
        setAttr("page", new Gb().dao().findPage(pageNumber,PAGE_SIZE));
        render(getView("gb/list"));
    }

    /**
     * 删除
     */
    public void delete() {
        Long ids[] = getParaValuesToLong("ids");
        if(ArrayUtils.isNotEmpty(ids)){
            for(Long id:ids){
                new Gb().dao().deleteById(id);
            }
        }
        renderJson(SUCCESS_FEEDBACK);
    }
}
