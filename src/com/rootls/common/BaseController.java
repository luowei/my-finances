package com.rootls.common;

import org.springframework.ui.Model;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 14-3-31
 * Time: 下午12:10
 * To change this template use File | Settings | File Templates.
 */
public class BaseController {

    public <T> Model addPageInfo(Model model, Page<T> page, String contextUrl) {

        int pageNumber = page.getPageNumber();
        int begin = Math.max(1, pageNumber - 5);
        int end = Math.min(begin + 10, page.getTotalPage());
        Integer totalPages = page.getTotalPage();
        Integer totalElements = page.getTotalCount();
        Integer pageSize = page.getPageSize();
        String order = page.getOrder();

        return model == null ? model : model.addAttribute("pageNumber", pageNumber)
                .addAttribute("beginIndex", begin)
                .addAttribute("endIndex", end)
                .addAttribute("totalPages", totalPages)
                .addAttribute("totalElements", totalElements)
                .addAttribute("contextUrl", contextUrl)
                .addAttribute("pageSize", pageSize)
                .addAttribute("order", order)
                .addAttribute("content",page.getContent());
    }

}
