package com.ats.securitymanager.repositories;

import com.ats.user.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM users " +
                    "INNER JOIN accounts ON users.id = accounts.id " +
                    "WHERE user_name=?1",
            nativeQuery = true)
    Optional<User> findUserByUsername(String username);
}
