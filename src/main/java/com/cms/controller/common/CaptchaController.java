package com.cms.controller.common;

import java.util.HashMap;
import java.util.Map;

import com.cms.routes.RouteMapping;
import com.jfinal.core.Controller;
@RouteMapping(url = "/common/captcha")
public class CaptchaController extends Controller{

    public void image(){
        renderCaptcha();
    }
    
    public void check(){
        boolean isOk = validateCaptcha("param");
        Map<String,Object> result = new HashMap<>();
        result.put("status", isOk==true?"y":"n");
        result.put("info", isOk==true?"验证通过":"验证不通过");
        renderJson(result);
    }
}
