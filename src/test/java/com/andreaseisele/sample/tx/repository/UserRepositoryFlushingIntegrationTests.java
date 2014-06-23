package com.andreaseisele.sample.tx.repository;

import com.andreaseisele.sample.tx.Application;
import com.andreaseisele.sample.tx.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

// note that this test suite is @Transactional but flushes changes manually
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserRepositoryFlushingIntegrationTests {

    private static final String TEST_EMAIL = "test@test.test";
    private static final String TEST_PASSWORD = "password";

    @Autowired
    private UserRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);
        assertThat(user.isNew(), is(true));

        user = repository.save(user);
        entityManager.flush();

        assertThat(user.isNew(), is(false));
        assertThat(user.getId(), is(notNullValue()));
        assertThat(user.getEmail(), is(equalTo(TEST_EMAIL)));
        assertThat(user.getPassword(), is(equalTo(TEST_PASSWORD)));
    }

    @Test
    public void testRead() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        entityManager.flush();

        User found = repository.findOne(user.getId());

        assertThat(found, is(equalTo(user)));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        entityManager.flush();

        user.setPassword("new");
        entityManager.flush();

        user = repository.save(user);

        assertThat(user.getPassword(), is(equalTo("new")));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User(TEST_EMAIL, TEST_PASSWORD);

        user = repository.save(user);
        entityManager.flush();

        repository.delete(user);
        entityManager.flush();

        User found = repository.findOne(user.getId());

        assertThat(found, is(nullValue()));
    }
    
}