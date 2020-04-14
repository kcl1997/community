package life.majiang.community.advice;

import com.alibaba.fastjson.JSON;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import life.majiang.community.dto.ResultDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;

/**
 * 项目名： community
 * 包名:    life.majiang.community.advice
 * 文件名   CustomizeExceprionHandler
 * 创建者
 * 创建时间: 2020/3/23 11:44 PM
 * 描述 本页面可以处理的异常,自定义异常
 */
@ControllerAdvice
public class CustomizeExceprionHandler {

    @ExceptionHandler(Exception.class)
    /**
     * 返回页面，和。。不能同时存在
     */
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response){
        String contentType = request.getContentType();


        /**
         *  回复异常时，返回 json 数据， 不让他自动跳转界面
         *  发送时是json数据
         */
        if("application/json".equals(contentType)){
            ResultDTO resultDTO = null;
            //返回json
            //如果是自己抛出来的异常
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException)e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                //写json数据
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return null;

        }else{

            //显示错误界面跳转
            // e --> throwable
            /**
             * 自己抛出来的异常
             */
            if(e instanceof CustomizeException){
                //自定义的异常
                model.addAttribute("message",e.getMessage());
            }else{
                //未知异常,好像用不到
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("/error");
        }

    }





}

