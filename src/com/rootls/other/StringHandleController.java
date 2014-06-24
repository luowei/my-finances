package com.rootls.other;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-1
 * Time: 下午6:46
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/other")
public class StringHandleController {

    @RequestMapping("/toTools")
    public String toTools(){
        return "other/tools";
    }

    /**
     * 删除字符串中的行符
     * @param source
     * @param regex
     * @return
     */
    @ResponseBody @RequestMapping("/removeLineNumber")
    public String removeLineNumber(String source, String regex) {
        String result = source.replaceAll(regex, "");
        return result;
    }

    @ResponseBody @RequestMapping("/removeEmptyLine")
    public String removeEmptyLine(String source) {
        String output = null;
        if (isNotBlank(source)) {
            output = source.replaceAll("((\r\n)|\n)[\\s\t ]*(\\1)+", "$1").replaceAll("^((\r\n)|\n)", "");
        }
        return output;
    }



}
