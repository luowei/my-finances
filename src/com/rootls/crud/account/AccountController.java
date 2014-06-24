package com.rootls.crud.account;

import com.rootls.common.BaseController;
import com.rootls.common.Config;
import com.rootls.common.Page;
import com.rootls.helper.ReadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.rootls.common.Config.secret_key;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/account")
public class AccountController  extends BaseController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ReadData readData;

    @ResponseBody
    @RequestMapping(value = "/add",method = POST,produces = "application/json; charset=utf-8")
    public String add(Account account){
        int sum = 0;
        if(!accountRepository.exsitSameAccount(account)){
            sum += accountRepository.add(account);
        } else {
            return "{\"code\":0,\"msg\":\"站点描述名称重复\"}";
        }
        return "{\"code\":1,\"msg\":\""+sum+"条记录添加成功\",\"num\":"+sum+"}";
    }

    @ResponseBody
    @RequestMapping(value = "/update",method = POST,produces = "application/json; charset=utf-8")
    public String update(Account account){
        int sum = 0;
        if(accountRepository.exsitById(account.getId())){
            sum += accountRepository.update(account);
        }else {
            return "{\"code\":0,\"msg\":\"不存在此记录,无法修改\"}";
        }
        return "{\"code\":1,\"msg\":\""+sum+"条记录修改成功\",\"num\":"+sum+"}";
    }

    @ResponseBody @RequestMapping("/del")
    public String del(Integer id){
        int sum = 0;
        if(accountRepository.exsitById(id)){
            sum += accountRepository.delById(id);
        } else {
            return "{\"code\":0,\"msg\":\"不存在此记录,无法删除\"}";
        }
        return "{\"code\":1,\"msg\":\""+sum+"条记录删除成功\",\"num\":"+sum+"}";
    }

    @ResponseBody @RequestMapping("/getBean")
    public Account getBean(Integer id){
        if(id!=null){
            return accountRepository.getById(id);
        }else {
            return null;
        }
    }

    @RequestMapping("/list")
    public String list(Model model,Account account,Page page){

        Page<Account> pg = accountRepository.list(account,page);

        addPageInfo(model,pg,"/account/list");

        return "account/list";
    }

    @ResponseBody @RequestMapping("/getKeyReal")
    public String getKeyReal(Integer id){
//        return accountRepository.getKeyReal(id, secret_key);
        return "{\"code\":1,\"msg\":\"ok\",\"content\":\""+accountRepository.getKeyReal(id, secret_key)+"\"}";
    }

    @ResponseBody
    @RequestMapping("/reloadAccounts")
    public String reloadAccounts(){
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();*/
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int num = accountRepository.delAllAccount();

        List<Account> list = readData.readAccountsFromZipFile(secret_key);
        int sum = 0;
        for(Account account:list){
            if(!accountRepository.exsitSameAccount(account)){
                sum += accountRepository.add(account);
            }
        }
        return "{\"code\":1,\"msg\":\""+sum+"条记录添加成功\",\"num\":"+sum+"}";
    }



}
