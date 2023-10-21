import com.book.bean.User;
import com.book.dao.BookDao;
import com.book.dao.BorrowDao;
import com.book.dao.UserDao;
import com.book.dao.impl.BorrowDaoImpl;
import com.book.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;

public class Test1 {
@Test
public void test()
    {
//        UserDao userDao = new UserDaoImpl();
//        User user = userDao.selectUserByUsername("admin");
//        System.out.println(user);
        BorrowDao borrowDao = new BorrowDaoImpl();
        boolean flag = borrowDao.selectBookisReturn("4edc1987c10d436aa1c21be87541ec81");
        System.out.println(flag);
    }
}
