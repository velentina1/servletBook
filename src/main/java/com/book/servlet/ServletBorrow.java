package com.book.servlet;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONUtil;
import com.book.bean.BorrowInfo;
import com.book.bean.PageResult;
import com.book.bean.ResultVo;
import com.book.bean.TreeMenu;
import com.book.service.BookService;
import com.book.service.BorrowService;
import com.book.service.impl.BookServiceImpl;
import com.book.service.impl.BorrowServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/borrow")
public class ServletBorrow extends BaseServlet {
    BorrowService borrowService = new BorrowServiceImpl();
    BookService bookService = new BookServiceImpl();

    public void borrowList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<BorrowInfo> borrowInfoList = borrowService.borrowList();
        int count = borrowService.count();
        PageResult pageResult = new PageResult<>();
        pageResult.setTotal(count);
        pageResult.setData(borrowInfoList);

        response.getWriter().write(JSONUtil.toJsonStr(pageResult));
    }

    public void returnBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String borrowid = request.getParameter("borrowid");
        boolean returnOK = borrowService.returnBook(borrowid);
        BorrowInfo borrowInfo = borrowService.selectBorrowById(borrowid);
        boolean updateOK = bookService.updateBookNumber(borrowInfo.getBookid());
        ResultVo<Object> resultVo = new ResultVo<>();
        if (updateOK && returnOK){
            resultVo.setOK(true);
            resultVo.setMessage("还书成功");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
    public void deleteBorrow(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String borrowid = request.getParameter("borrowid");

        BorrowInfo borrowInfo = borrowService.selectBorrowById(borrowid);
        ResultVo<Object> resultVo = new ResultVo<>();
        if (borrowInfo.getIsreturn() == 1){
            boolean deleteOK = borrowService.deleteBorrow(borrowid);
            if (deleteOK){
                resultVo.setOK(true);
                resultVo.setMessage("删除成功");
            }
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
    public void queryTreeMenus(HttpServletRequest request, HttpServletResponse response) throws IOException{
        List<TreeMenu> treeMenus = borrowService.queryTreeMenus();
        response.getWriter().write(JSONUtil.toJsonStr(treeMenus));
    }

    public void addBorrow(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String data = request.getParameter("data");
        ResultVo<Object> resultVo = new ResultVo<>();
        BorrowInfo borrowInfo = JSONUtil.toBean(data, BorrowInfo.class);
        boolean borrowOK = bookService.getBookRemain(borrowInfo.getBookid());
        if (borrowOK){
            boolean addOK = borrowService.addBorrow(borrowInfo);
            if (addOK){
                resultVo.setOK(true);
                resultVo.setMessage("借阅成功！");
                bookService.updateBookCount(borrowInfo.getBookid());
            }
        }else {
            resultVo.setMessage("库存不足！！");
        }
        response.getWriter().write(JSONUtil.toJsonStr(resultVo));
    }
}