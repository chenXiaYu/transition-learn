import com.entitty.User;
import com.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TransitionTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public  void testSave(){
    User user = new User();
    user.setUserName("chen");
    user.setPassWord("222");
    user.setRealName("cech");
    userService.saveUser(user);
    }

    @Test
    public void testTramsfer(){
      try {
          User from = userService.getById(1);
          User to = userService.getById(3);
          System.out.println(from.toString());
          System.out.println(to.toString());

          userService.transfer(1,3,new BigDecimal(100));

          from = userService.getById(1);
          to = userService.getById(3);
          System.out.println(from.toString());
          System.out.println(to.toString());
      }catch (Exception e){
          System.out.println("error");
      }
        new Thread(()->{
            User user = new User();
            user.setUserName("chen");
            user.setPassWord("222");
            user.setRealName("ggg");
            userService.saveUser(user);
        }).start();
    }

    @Test
    public void testTramsfer2(){
        try {
            User from = userService.getById(1);
            User to = userService.getById(3);
            System.out.println(from.toString());
            System.out.println(to.toString());

            userService.transfer2(1,3,new BigDecimal(100));

            from = userService.getById(1);
            to = userService.getById(3);
            System.out.println(from.toString());
            System.out.println(to.toString());
        }catch (Exception e){
            System.out.println("error");
        }
    }

    @Test
    public void testTramsfer3(){
        try {
            User from = userService.getById(1);
            User to = userService.getById(3);
            System.out.println(from.toString());
            System.out.println(to.toString());

            userService.transfer3(1,3,new BigDecimal(100));

            from = userService.getById(1);
            to = userService.getById(3);
            System.out.println(from.toString());
            System.out.println(to.toString());
        }catch (Exception e){
            System.out.println("error");
        }
    }
}
