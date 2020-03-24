package life.majiang.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import life.majiang.community.exception.CustomizeException;

/**
 * 项目名： community
 * 包名:    life.majiang.community.advice
 * 文件名   CustomizeExceprionHandler
 * 创建者
 * 创建时间: 2020/3/23 11:44 PM
 * 描述  ${TODO}
 */
@ControllerAdvice
public class CustomizeExceprionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){

        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else{
            model.addAttribute("message","服务冒烟了，要不然你稍后再试试");
        }

        return new ModelAndView("/error");
    }





}

