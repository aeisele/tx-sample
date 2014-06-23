package com.andreaseisele.sample.tx.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * @author <a href="mailto:ae@andreaseisele.com">Andreas Eisele</a>
 */
@Entity
public class User extends AbstractPersistable<Long> {

    private String email;

    // not-encrypted -> do not use in production!
    private String password;

    private User() {
        // for hibernate
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
