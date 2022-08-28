import com.zagvladimir.domain.Item;
import com.zagvladimir.domain.User;
import com.zagvladimir.repository.user.UserRepositoryInterface;
import com.zagvladimir.service.ItemService;
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
        ItemService itemService = annotationConfigApplicationContext.getBean(ItemService.class);


        List<User> all = userService.findAll();
        for (User user : all) {
            System.out.println(user);
        }
        System.out.println("--------------------------------------------------");


        User user1 = userService.findById(4L);
        System.out.println(user1);


//        System.out.println("--------------------------------------------------");
//        Item newItem1 = new Item();
//        newItem1.setItem_name("Makita hr2450ft");
//        newItem1.setItem_type_id(5);
//        newItem1.setLocation_id(2);
//        newItem1.setItem_location("Microdistrict 20");
//        newItem1.setDescription("Rotary Hammer makita hr2450");
//        newItem1.setOwner_id(3);
//        newItem1.setPrice_per_hour(5);
//        newItem1.setAvailable(true);
//        newItem1.setCreation_date(new Timestamp(new Date().getTime()));
//        newItem1.setModification_date(new Timestamp(new Date().getTime()));
//        System.out.println(newItem1);
//
//        System.out.println(itemService.create(newItem1));
//
//        System.out.println("--------------------------------------------------");
//        Item newItem2 = new Item();
//        newItem2.setItem_name("Bosch GBH 2-26");
//        newItem2.setItem_type_id(5);
//        newItem2.setLocation_id(2);
//        newItem2.setItem_location("Microdistrict 16");
//        newItem2.setDescription("Rotary Hammer Bosch GBH 2-26 DFR 0.611.254.768");
//        newItem2.setOwner_id(3);
//        newItem2.setPrice_per_hour(6);
//        newItem2.setAvailable(true);
//        newItem2.setCreation_date(new Timestamp(new Date().getTime()));
//        newItem2.setModification_date(new Timestamp(new Date().getTime()));
//        System.out.println(itemService.create(newItem2));


        System.out.println("--------------------------------------------------");

        List<Item> allItems = itemService.findAll();
        for (Item item : allItems) {
            System.out.println(item);
        }

        System.out.println("--------------------------------------------------");

        List<Item> searchItems = itemService.getItemsByCategory(4);
        for (Item item : searchItems) {
            System.out.println(item);
        }

        System.out.println("--------------------------------------------------");

        List<Item> searchItemsByName = itemService.searchItemsByName("bosch");
        for (Item item : searchItemsByName) {
            System.out.println(item);
        }

        System.out.println("--------------------------------------------------");
//        user1.setEmail("olegpopkov@gmail.com");
//        System.out.println(userService.update(user1));

//        User newUser = new User();
//        newUser.setUsername("Billi");
//        newUser.setUser_password("435342");
//        newUser.setLocation_id(2);
//        newUser.setLocation_details("Stavropolskaya st 6-2");
//        newUser.setPhone_number("80233452145");
//        newUser.setMobile_number("+3752964366661");
//        newUser.setEmail("billimilligan@yahoo.com");
//        newUser.setRegistration_date(new Timestamp(new Date().getTime()));
//        newUser.setCreation_date(new Timestamp(new Date().getTime()));
//        newUser.setModification_date(new Timestamp(new Date().getTime()));
//
//        System.out.println(userService.create(newUser));

//        System.out.println(userService.delete(10L));

    }
}