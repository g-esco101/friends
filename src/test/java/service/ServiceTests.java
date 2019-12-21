package service;

import com.wiredbrains.friends.FriendsApplication;
import com.wiredbrains.friends.model.Friend;
import com.wiredbrains.friends.service.FriendService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // This would be unnecessary for an embedded database.
@SpringBootTest(classes = FriendsApplication.class)
public class ServiceTests {

    @Autowired
    FriendService friendService;

    @Test
    public void testCreateReadDelete() {
        Friend friend = new Friend("Gordon", "Moore", 33, false, null);

        friendService.save(friend);

        Iterable<Friend> friends = friendService.findAll();
        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Gordon");

        friendService.deleteAll();
        Assertions.assertThat(friendService.findAll()).isEmpty();
    }
}