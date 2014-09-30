package com.rootls.crud.regex;

import com.rootls.helper.ReadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-4-5
 * Time: 上午11:03
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/regex")
public class RegexController {

    @Autowired
    RegexTipRepository regexTipRepository;

    @Autowired
    ReadData readData;

    @RequestMapping("/list")
    public String list(Model model, RegexTip regexTip) {

        List<RegexTip> list = regexTipRepository.list(regexTip);

        model.addAttribute("list", list);
        return "regex/list";
    }

    @ResponseBody
    @RequestMapping(value = "add", method = POST, produces = "application/json; charset=utf-8")
    public String add(RegexTip regexTip) {
        int sum = 0;
        if (regexTip.getName() != null && regexTip.getDescribe() != null &&
                !regexTipRepository.exsitSameRegex(regexTip)) {
            sum += regexTipRepository.add(regexTip);
        } else {
            return "{code:0,msg:\"记录重复\"}";
        }
        return "{code:1,msg:\"" + sum + "条记录添加成功\",num:" + sum + "}";
    }

    @ResponseBody
    @RequestMapping(value = "/update"
            , method = POST, produces = "application/json; charset=utf-8")
    public String update(RegexTip regexTip) {
        int sum = 0;
        if (regexTipRepository.exsitById(regexTip.getId())) {
            sum += regexTipRepository.update(regexTip);
        } else {
            return "{code:0,msg:\"不存在此记录,无法修改\"}";
        }
        return "{code:1,msg:\"" + sum + "条记录修改成功\",num:" + sum + "}";
    }

    @ResponseBody
    @RequestMapping("/del")
    public String del(Integer id) {
        int sum = 0;
        if (regexTipRepository.exsitById(id)) {
            sum += regexTipRepository.delById(id);
        } else {
            return "{\"code\":0,\"msg\":\"不存在此记录,无法删除\"}";
        }
        return "{\"code\":1,\"msg\":\"" + sum + "条记录删除成功\",\"num\":" + sum + "}";
    }

    @ResponseBody
    @RequestMapping("/getRegex")
    public RegexTip getRegex(Integer id) {
        if (id != null) {
            return regexTipRepository.getRegex(id);
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/reloadRegex")
    public String reloadAccounts(){
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();*/
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int num = regexTipRepository.delAll();

        List<RegexTip> list = readData.readRegexTipFile();
        int sum = 0;
        for(RegexTip regexTip:list){
                sum += regexTipRepository.add(regexTip);
        }
        return "{\"code\":1,\"msg\":\""+sum+"条记录添加成功\",\"num\":"+sum+"}";
    }

}
