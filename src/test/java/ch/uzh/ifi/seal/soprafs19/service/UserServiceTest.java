package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.Application;
import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class)
public class UserServiceTest {


    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByUsername("testUsername"));

        User testUser = new User();
        User testUser1 = new User();
        User testUser2 = new User();
        User testUser3 = new User();

        testUser.setUsername("wombat");
        testUser.setPassword("hallo");
        User createdUser = userService.createUser(testUser);
        Assert.assertNotNull(createdUser.getToken());
        Assert.assertEquals(createdUser.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser, userRepository.findByToken(createdUser.getToken()));

        testUser1.setUsername("");
        testUser1.setPassword("hallo");
        User createdUser1 = userService.createUser(testUser);
        Assert.assertNotNull(createdUser1.getToken());
        Assert.assertEquals(createdUser1.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser1, userRepository.findByToken(createdUser1.getToken()));

        testUser2.setUsername("wombat");
        testUser2.setPassword("");
        User createdUser2 = userService.createUser(testUser);
        Assert.assertNotNull(createdUser2.getToken());
        Assert.assertEquals(createdUser2.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser2, userRepository.findByToken(createdUser2.getToken()));

        testUser3.setUsername("");
        testUser3.setPassword("hallo");
        User createdUser3 = userService.createUser(testUser);
        Assert.assertNotNull(createdUser3.getToken());
        Assert.assertEquals(createdUser3.getStatus(),UserStatus.ONLINE);
        Assert.assertEquals(createdUser3, userRepository.findByToken(createdUser3.getToken()));
    }

}
