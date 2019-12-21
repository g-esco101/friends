package unit;

import com.wiredbrains.friends.FriendsApplication;
import com.wiredbrains.friends.controller.FriendController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// command to run tests: mvn test
// command to run single test: mvn -Dtest=FriendsApp* test

@RunWith(SpringRunner.class) // Means that junit is used.
@SpringBootTest(classes = FriendsApplication.class) // Spring boot application is started inside the test.
class FriendsApplicationTests {

    @Autowired
    FriendController friendController;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(friendController);
    }
}