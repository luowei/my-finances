package com.rootls.other;

import com.rootls.common.BaseController;
import com.rootls.common.InerCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by luowei on 2014/4/13.
 */
@Controller
@RequestMapping("/cache")
public class CacheController {

    @ResponseBody
    @RequestMapping("/clear")
    public String innerCacheClear(){
        InerCache.clearCache();
        return "{\"code\":1,\"msg\":\"ok\"}";
    }


}
