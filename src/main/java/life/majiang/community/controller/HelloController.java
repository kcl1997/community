package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   HelloController
 * 创建者
 * 创建时间: 2020/3/14 10:50 PM
 * 描述  ${TODO}
 */
@Controller
public class HelloController {

   @GetMapping("/hello")
   public String hello(@RequestParam(name = "name")String name, Model
                       model){

      model.addAttribute("name",name);
      return "hello";

   }

}

