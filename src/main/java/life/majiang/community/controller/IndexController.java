package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.service.QuestionService;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   HelloController
 * 创建者
 * 创建时间: 2020/3/14 10:50 PM
 * 描述  ${TODO}
 */
@Controller
public class IndexController {

   @Autowired
   private QuestionService questionService;

   /**
    * 显示主页问题总的列表
    * @param model
    * @param page
    * @param size
    * @return
    */
   @GetMapping(value = {"/","/index"})
   public String index( Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "size",defaultValue = "10")Integer size){

      //显示列表信息
      PaginationDTO pagination = questionService.list(page,size);

      model.addAttribute("pagination",pagination);
      return "index";
   }


}

