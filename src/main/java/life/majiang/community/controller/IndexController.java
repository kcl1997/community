package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;

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


   @GetMapping(value = {"/","/index"})
   public String index(HttpServletRequest request){

      try {
         Cookie[] cookies = request.getCookies();
         if(cookies != null){
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


      return "index";
   }
//
//   @GetMapping("/index")
//   public String index2(){
//      return "index";
//   }

}

