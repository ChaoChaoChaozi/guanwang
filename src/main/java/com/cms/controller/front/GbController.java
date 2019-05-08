package com.cms.controller.front;

import java.util.Date;
import java.util.HashMap;

import com.cms.Feedback;
import com.cms.entity.Gb;
import com.cms.routes.RouteMapping;
import com.cms.util.IPUtils;

@RouteMapping(url = "/gb")
public class GbController extends BaseController {
    
    /**
     * 保存
     */
    public void save() {
       String ip = IPUtils.getIpAddress(getRequest());
       Gb gb = getModel(Gb.class,"",true);
       gb.setIp(ip);
       gb.setCreateDate(new Date());
       gb.setModifyDate(new Date());
       gb.save();
       renderJson(Feedback.success(new HashMap<>()));
    }

}
