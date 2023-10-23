package com.book.servlet;

import cn.hutool.json.JSONUtil;
import com.book.bean.Book;
import com.book.bean.BookType;
import com.book.bean.PageResult;
import com.book.bean.ResultVo;
import com.book.service.BookService;
import com.book.service.BorrowService;
import com.book.service.impl.BookServiceImpl;
import com.book.service.impl.BorrowServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/books")
public class ServletBook extends BaseServlet{
    private BookService bookService = new BookServiceImpl();
    private BorrowService borrowService = new BorrowServiceImpl();
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
    public void addBook(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String data = request.getParameter("data");
        Book book = JSONUtil.toBean(data, Book.class);
        boolean flag = bookService.repeatBook(book.getAuthor(),book.getBookname());
        ResultVo<Object> resultVo = new ResultVo<>();
        if (flag){
            resultVo.setMessage("作者书名重复！");
        }else {
            boolean addOK = bookService.addBook(book);
            if (addOK){
                resultVo.setOK(true);
                resultVo.setMessage("添加成功！");
            }
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
    public void updateBook(HttpServletRequest request,HttpServletResponse response) throws IOException{
       String data = request.getParameter("data");
       Book book = JSONUtil.toBean(data, Book.class);
       ResultVo<Object> resultVo = new ResultVo<>();
       boolean flag = bookService.updateBook(book);
       if (flag) {
           resultVo.setOK(true);
           resultVo.setMessage("修改图书成功");
       }
       response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
    public void deleteBook(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String ids = request.getParameter("ids");
        ResultVo<Object> resultVo = new ResultVo<>();
        boolean isReturn = borrowService.selectBookIsReturn(ids);
        if (isReturn){
            resultVo.setMessage("存在未归还图书，不能删除！");
        } else{
            boolean deleteOk = bookService.deleteBook(ids);
            if (deleteOk){
                resultVo.setOK(true);
                resultVo.setMessage("删除成功！");
            }
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
    public void queryById(HttpServletRequest request,HttpServletResponse response) throws IOException{
       String bookid = request.getParameter("bookid");
       Book book = bookService.selectBookById(bookid);
       response.getWriter().write(JSONUtil.toJsonStr(book));
    }
}
