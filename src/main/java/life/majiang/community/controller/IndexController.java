package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
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
   private UserMapper userMapper;
   @Autowired
   private QuestionService questionService;


   @GetMapping(value = {"/","/index"})
   public String index(HttpServletRequest request, Model model,
                       @RequestParam(name = "page",defaultValue = "1")Integer page,
                       @RequestParam(name = "size",defaultValue = "10")Integer size){

      try {
         Cookie[] cookies = request.getCookies();
         if(cookies != null && cookies.length > 0){
            for(Cookie cookie : cookies){
               if(cookie.getName().equals("token")){
                  String token = cookie.getValue();
                  User user = userMapper.findByToken(token);
                  if(user != null) request.getSession().setAttribute("user",user);
                  break;
               }
            }
         }
      }catch (Exception e){}


      //显示列表信息
      PaginationDTO pagination = questionService.list(page,size);

      model.addAttribute("pagination",pagination);
      return "index";
   }


}

