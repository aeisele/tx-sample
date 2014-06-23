package com.andreaseisele.sample.tx.repository;

import com.andreaseisele.sample.tx.Application;
import com.andreaseisele.sample.tx.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// note that this test suite is not @Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserRepositoryIntegrationTests {

    private static final String TEST_EMAIL = "test@test.test";
    private static final String TEST_PASSWORD = "password";

    @Autowired
    private UserRepository repository;

    @Test
    public void testCreate() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);
        assertThat(user.isNew(), is(true));

        user = repository.save(user);

        assertThat(user.isNew(), is(false));
        assertThat(user.getId(), is(notNullValue()));
        assertThat(user.getEmail(), is(equalTo(TEST_EMAIL)));
        assertThat(user.getPassword(), is(equalTo(TEST_PASSWORD)));
    }

    @Test
    public void testRead() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        User found = repository.findOne(user.getId());

        assertThat(found, is(equalTo(user)));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        user.setPassword("new");
        user = repository.save(user);

        assertThat(user.getPassword(), is(equalTo("new")));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        repository.delete(user);
        User found = repository.findOne(user.getId());

        assertThat(found, is(nullValue()));
    }
    
}