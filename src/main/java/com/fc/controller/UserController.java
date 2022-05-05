package com.fc.controller;

import com.fc.entity.Admin;
import com.fc.entity.CustomAccount;
import com.fc.service.LoginService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController

@RequestMapping("login")
public class UserController {
    public LoginService loginService;
    //管理员登录
    @RequestMapping("adminLogin")
    public ModelAndView adminLogin(ModelAndView mv,
                                   HttpServletResponse response,
                                   HttpSession session,
                                   Cookie cookie,
                                   Admin admin
                                   ){
//调用业务层的登录方法
        ResultVO resultVO = loginService.adminLogin(admin.getName(),admin.getPassword());
    if (resultVO.getCode()==200){

        session.setAttribute("admin",resultVO.getData());
        cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setMaxAge(30*60);
        try {
            response.sendRedirect("resources/META-INF/static/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }else {
        cookie.setMaxAge(-1);
        mv.addObject("resultInfo",resultVO);

        try {
            response.getWriter().write("<script charset='UTF-8'>alert(\"用户名或密码错误！\");" +
                    "location.href='index.jsp';</script>");
            response.sendRedirect("resources/META-INF/static/index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        response.addCookie(cookie);
        return mv;
    }
    //管理员退出登录
    @GetMapping("adminLogout")
    public ModelAndView adminLogout(ModelAndView mv,
                                    HttpSession session){
        session.removeAttribute("admin");
        mv.setViewName("forward:login.jsp");
        return mv;
    }
    //用户登录
    @RequestMapping("CustomAccount")
    public ModelAndView CustomAccount(ModelAndView mv,
                                      HttpSession session,
                                      HttpServletResponse response,
                                      Cookie cookie,
                                      CustomAccount customAccount,
                                      HttpServletRequest request){
       ResultVO resultVO = loginService.customAccountLogin(customAccount.getUsername(),customAccount.getPassword());
       if (resultVO.getCode()==200){
           session.setAttribute("customAccount",resultVO.getData());
           cookie = new Cookie("JSESSIONID", session.getId());
           cookie.setMaxAge(30*60);
           mv.setViewName("redirect:index2.jsp");
       }else {
           cookie.setMaxAge(-1);
           mv.addObject("resultInfo",resultVO);
           mv.setViewName("redirect:login.jsp");
       }
       response.addCookie(cookie);
        return mv;
    }
    //用户退出登录
    @GetMapping("customAccountLogout")
    public ModelAndView customAccountLogout(ModelAndView mv,
                                    HttpSession session){
        session.removeAttribute("customAccount");
        mv.setViewName("forward:login.jsp");
        return mv;
    }
}
