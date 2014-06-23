package com.andreaseisele.sample.tx.repository;

import com.andreaseisele.sample.tx.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:ae@andreaseisele.com">Andreas Eisele</a>
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
