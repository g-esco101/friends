package system;

import com.wiredbrains.friends.FriendsApplication;
import com.wiredbrains.friends.model.Friend;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FriendsApplication.class, webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTests {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void testCreateReadDelete() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/friend";
        URI uri = new URI(baseUrl);

        Friend friend = new Friend("Gordon", "Moore", 33, false, null);
        ResponseEntity<Friend> entity = restTemplate.postForEntity(uri, friend, Friend.class);

        Friend[] friends = restTemplate.getForObject(uri, Friend[].class);
        Assertions.assertThat(friends).extracting(Friend::getFirstName).containsOnly("Gordon");

        restTemplate.delete(uri + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(uri, Friend[].class)).isEmpty();
    }

    @Test
    public void testErrorHandlingReturnsBadRequest() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/friend";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
