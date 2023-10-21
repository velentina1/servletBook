package com.book.dao.impl;

import com.book.bean.BorrowInfo;
import com.book.dao.BorrowDao;
import com.book.util.DruidUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {
    private QueryRunner queryRunner = new QueryRunner(DruidUtil.getDataSource());
    @Override
    public boolean selectBookisReturn(String ids) {
        String[] idsArray = ids.split(",");
        String sql = "select * from tb_borrowinfo where bookid in(?)";
        try {
            List<BorrowInfo> borrowInfoList = queryRunner.query(sql, new BeanListHandler<>(BorrowInfo.class),idsArray);
            for (BorrowInfo borrowInfo : borrowInfoList){
                if (borrowInfo.getIsreturn() == 0){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
