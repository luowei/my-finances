package com.rootls.crud.finance;

import com.rootls.common.BaseController;
import com.rootls.common.Page;
import com.rootls.helper.ReadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-29
 * Time: 上午2:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/finance")
public class DaytipController extends BaseController {

    @Autowired
    DaytipRepository daytipRepository;

    @Autowired
    ReadData readData;

    @ResponseBody
    @RequestMapping("/reloadDaytip")
    public String reloadDaytip(String tableName) {
        if(isBlank(tableName)){
            tableName = "daytip";
        }

        try {
            daytipRepository.delDaytip(tableName);

            List<Daytip> list = getDaytips();
            if(tableName.equals("daytipDetail")){
                for (Daytip tip : list) {
                    daytipRepository.addDaytip(tip, tableName);
                }
            }else {
                for (Daytip tip : list) {
                    if (!daytipRepository.exsitDaytip(tip.getTipDateStr(), tableName)) {
                        daytipRepository.addDaytip(tip, tableName);
                    } else {
                        daytipRepository.appendDaytipMoney(tip, tableName);
                    }
                }
            }

            daytipRepository.updateTipDate(2014, tableName);

            return "{\"code\":1,\"msg\":\"加载"+tableName+"数据成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":0,\"msg\":\"加载"+tableName+"数据失败：" + e.getMessage() + "\"}";
        }

    }

    private List<Daytip> getDaytips() {
        List<Daytip> list;
        try {
            list = readData.readTipsFromUrl();
        } catch (Exception e) {
            e.printStackTrace();
            list = readData.readTipsFromFile();
        }
        if (list == null || list.size() == 0) {
            list = readData.readTipsFromFile();
        }
        return list;
    }

    @RequestMapping("/list")
    public String listDaytip(Model model, Daytip daytip, String tableName, Page page) {

        Page<Daytip> pg = daytipRepository.pageDaytip(daytip, isBlank(tableName) ? "daytip" : tableName, page);

        addPageInfo(model, pg, "/finance/list")
                .addAttribute("moneySum", pg.getExtra().get("moneySum"))
                .addAttribute("tableName", tableName);

        return "finance/list";
    }

    @ResponseBody
    @RequestMapping("/addDaytip")
    public String addDaytip(Daytip tip) {
        String tableName = "daytip";
        if (!daytipRepository.exsitDaytip(tip.getTipDateStr(), tableName)) {
            daytipRepository.addDaytip(tip, tableName);
        }
        return "{\"code\":1,\"msg\":\"添加Daytip成功\"}";
    }

}
