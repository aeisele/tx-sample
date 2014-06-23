package com.andreaseisele.sample.tx.service;

import com.andreaseisele.sample.tx.Application;
import com.andreaseisele.sample.tx.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

// note that this test suite is @Transactional
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceIntegrationTests {

    private static final String TEST_EMAIL = "test@test.test";
    private static final String TEST_PASSWORD = "password";

    @Autowired
    private UserService service;

    private Wiser wiser;

    @Before
    public void setUp() throws Exception {
        this.wiser = new Wiser(2500);
        wiser.start();
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }

    @Test
    public void testRegister() throws Exception {
        User user = service.register(TEST_EMAIL, TEST_PASSWORD);

        assertThat(user.getEmail(), is(equalTo(TEST_EMAIL)));
        assertThat(user.getPassword(), is(equalTo(TEST_PASSWORD)));

        List<WiserMessage> messages = wiser.getMessages();
        assertThat(messages, hasSize(1));

        WiserMessage message = messages.get(0);
        assertThat(message.getEnvelopeReceiver(), is(equalTo(TEST_EMAIL)));
    }

    @Test
    public void testRegister_invalidEmail() throws Exception {
        User user = service.register("invalüd@test.test", TEST_PASSWORD);

        assertThat(user.getEmail(), is(equalTo("invalüd@test.test")));
        assertThat(user.getPassword(), is(equalTo(TEST_PASSWORD)));

        // no email -> exception should be logged instead
        List<WiserMessage> messages = wiser.getMessages();
        assertThat(messages, hasSize(0));
    }

}