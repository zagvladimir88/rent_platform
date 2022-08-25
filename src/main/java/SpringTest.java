import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import com.zagvladimir.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SpringTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("com.zagvladimir");
        UserService userService = annotationConfigApplicationContext.getBean(UserService.class);

        List<User> all = userService.findAll();
        for (User user : all) {
            System.out.println(user);
        }



        User user1 = userService.findById(4L);
        System.out.println(user1);

//        user1.setEmail("olegpopkov@gmail.com");
//        System.out.println(userService.update(user1));

//        User newUser = new User();
//        newUser.setUsername("John");
//        newUser.setUser_password("1111");
//        newUser.setLocation_id(2);
//        newUser.setLocation_details("Lenin st 15-2");
//        newUser.setPhone_number("80233452352");
//        newUser.setMobile_number("+3752964364521");
//        newUser.setEmail("johnwilliams@yahoo.com");
//        newUser.setRegistration_date(new Timestamp(new Date().getTime()));
//        newUser.setCreation_date(new Timestamp(new Date().getTime()));
//        newUser.setModification_date(new Timestamp(new Date().getTime()));
//
//        System.out.println(userService.create(newUser));

//        System.out.println(userService.delete(10L));

    }
}