package com.book.servlet;

import cn.hutool.json.JSONUtil;
import com.book.bean.ResultVo;
import com.book.bean.User;
import com.book.service.UserService;
import com.book.service.impl.UserServiceImpl;
import com.book.util.FormBeanUtil;
import com.book.util.MD5Util;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Map;

@WebServlet("/user")
public class ServletUser extends BaseServlet {
    UserService userService = new UserServiceImpl();
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ResultVo ResultVo = new ResultVo();


        HttpSession session = request.getSession();
        String code1 = (String) session.getAttribute("code");

        String code = request.getParameter("code");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!code.equalsIgnoreCase(code1)){
            ResultVo.setMessage("验证码错误！");
        }else {
            User user = userService.selectUserByName(username);
            if (user == null){
                ResultVo.setMessage("用户不存在");
            }else {
                String password1 = user.getPassword();
                boolean flag = MD5Util.verifySaltPassword(password, password1);
                if (!flag){
                    ResultVo.setMessage("用户密码错误");
                }else{
                    ResultVo.setOK(true);
                    session.setAttribute("user",user);
                }
            }
        }
        response.getWriter().write(JSONUtil.toJsonStr(ResultVo));
    }

    public void verifyUsername (HttpServletRequest request,HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        User user = userService.selectUserByName(username);
        ResultVo resultVo = new ResultVo();

        if (user != null){
            resultVo.setOK(true);
            resultVo.setMessage("该用户名已被注册！");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }

    public void register (HttpServletRequest request,HttpServletResponse response) throws IOException {
        ResultVo resultVo = new ResultVo();
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = FormBeanUtil.formToBean(parameterMap, User.class);
       boolean addOK = userService.addUser(user);
       if (addOK){
           resultVo.setOK(true);
           resultVo.setMessage("注册成功");
       } else {
           resultVo.setOK(false);
           resultVo.setMessage("注册失败");
       }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
}

