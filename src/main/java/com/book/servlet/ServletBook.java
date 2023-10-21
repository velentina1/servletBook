package com.book.servlet;

import cn.hutool.json.JSONUtil;
import com.book.bean.Book;
import com.book.bean.BookType;
import com.book.bean.PageResult;
import com.book.service.BookService;
import com.book.service.impl.BookServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class ServletBook extends BaseServlet{
    private BookService bookService = new BookServiceImpl();
   public void bookList(HttpServletRequest request,HttpServletResponse response) throws IOException{
       String bookname = request.getParameter("bookname");
       String booktype = request.getParameter("booktype");

       int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
       int pageSize = Integer.parseInt(request.getParameter("pageSize"));

       List<Book> bookList = bookService.bookList(pageSize,pageIndex,bookname,booktype);

       int total = bookService.count();

       PageResult<Book> pageResult = new PageResult<>();
       pageResult.setTotal(total);
       pageResult.setData(bookList);

       response.getWriter().write(JSONUtil.toJsonStr(pageResult));
   }

    public void queryAllBookTypes(HttpServletRequest request,HttpServletResponse response) throws IOException{
       List<BookType> bookTypeList = bookService.queryBookTypes();
       response.getWriter().write(JSONUtil.toJsonStr(bookTypeList));
    }

}
