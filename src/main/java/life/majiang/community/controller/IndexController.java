package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

   @GetMapping("/")
   public String index(){
      return "index";
   }

}

