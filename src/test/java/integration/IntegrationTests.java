package integration;


import com.wiredbrains.friends.FriendsApplication;
import com.wiredbrains.friends.controller.FriendController;
import com.wiredbrains.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FriendsApplication.class)
public class IntegrationTests {

    @Autowired
    FriendController friendController;

    @Test
    public void testCreateReadDelete() {
        Friend friend = new Friend("Gordon", "Moore", 33, false, null);

        Friend friendResult = friendController.create(friend);

        Iterable<Friend> friends = friendController.read();
        Assertions.assertThat(friends).first().hasFieldOrPropertyWithValue("firstName", "Gordon");

        friendController.delete(friendResult.getId());
        Assertions.assertThat(friendController.read()).isEmpty();
    }

    @Test(expected = ValidationException.class)
    public void errorHandlingValidationExceptionThrown() {
        friendController.somethingIsWrong();

    }

}
