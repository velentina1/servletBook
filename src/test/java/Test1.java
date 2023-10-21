import com.book.bean.User;
import com.book.dao.UserDao;
import com.book.dao.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;

public class Test1 {
@Test
public void test()
    {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectUserByUsername("admin");
        System.out.println(user);
    }
}
