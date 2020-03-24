package life.majiang.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHubUser;
import life.majiang.community.model.User;
import life.majiang.community.provider.GItHubProvider;
import life.majiang.community.service.UserService;

/**
 * 项目名： community
 * 包名:    life.majiang.community.controller
 * 文件名   AuthorizeController
 * 创建者
 * 创建时间: 2020/3/15 5:22 PM
 * 描述  ${TODO}
 */
@Controller
public class AuthorizeController {

    @Autowired
   private GItHubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    /**
     *     第二步，用户点击登录按钮返回一个callback地址和code，根据code，获取用户的accessToken
     */
   @GetMapping("/callback")
    public ModelAndView callback(@RequestParam(name = "code") String code, @RequestParam(name = "state")String state,
                                 HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
       AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
       accessTokenDTO.setCode(code);
       accessTokenDTO.setRediret_uri(redirectUri);
       accessTokenDTO.setState(state);
       accessTokenDTO.setClient_id(clientId);
       accessTokenDTO.setClient_secret(clientSecret);

       /**
        *  根据当前网址，用户id，sercret，获得用户的access_token字符串
        */
       String accessToken = githubProvider.getAccessToken(accessTokenDTO);

       //根据accessToken 获取 User信息
       GitHubUser gitHubUser = githubProvider.getUser(accessToken);
       System.out.println(gitHubUser);

       if(gitHubUser != null && gitHubUser.getLogin().length() > 0 && gitHubUser.getId() != null){
           /**
            * 从数据库检查是否存在该用户
            */
           User user = new User();
           //使用token辨认用户
           String token = UUID.randomUUID().toString();
           user.setToken(token);
           user.setName(gitHubUser.getLogin());
           user.setAccountId(String.valueOf(gitHubUser.getId()));
           user.setAvatarUrl(gitHubUser.getAvatarUrl());
           userService.createOrUpdaete(user);


           //登录成功，写cookie和session
           request.getSession().setAttribute("user",user);


           //浏览器本地添加cookie
           Cookie cookie = new Cookie("token",token);
           cookie.setMaxAge(30*24*3600);
           response.addCookie(cookie);
           //跳转至首页
           ModelAndView modelAndView = new ModelAndView("redirect:index");
           redirectAttributes.addFlashAttribute("msg", "登录成功");
           return modelAndView;


       }else{

           ModelAndView modelAndView = new ModelAndView("redirect:index");
           redirectAttributes.addFlashAttribute("msg", "登录失败");
           return modelAndView;
       }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
       //移除session
       request.getSession().removeAttribute("user");
       //移除cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

       return "redirect:/index";
    }
}

