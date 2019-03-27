import co.b4pay.admin.dao.TUserDao;
import co.b4pay.admin.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring/spring-*.xml")
public class DaoTest {

    @Autowired
    private TUserDao tUserDao;

    @Test
    public void saveTest() {
        TUser tUser = new TUser();
        tUser.setAccount("1211");
        tUser.setUserid("13412431");
        tUser.setPayType(1);
        tUser.setStatus(0);
        int i = tUserDao.insert(tUser);
        System.out.println(i);
    }

    @Test
    public void findTest() {
        TUser tUser = tUserDao.get("18672679757");
        System.out.println(tUser);
    }
}
