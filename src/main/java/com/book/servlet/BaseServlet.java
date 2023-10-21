package com.book.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse rs){
        try {
            req.setCharacterEncoding("utf-8");
            rs.setContentType("text/html;charset=utf-8");
            String methodName = req.getParameter("method");
            Method method =
                    this.getClass().getMethod(methodName,
                            HttpServletRequest.class,
                            HttpServletResponse.class);
            method.invoke(this,req,rs);
            this.getClass().getMethod(methodName);
        }catch (UnsupportedEncodingException | NoSuchMethodException | InvocationTargetException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
