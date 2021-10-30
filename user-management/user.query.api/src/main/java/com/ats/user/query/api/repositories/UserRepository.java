package com.ats.user.query.api.repositories;

import com.ats.user.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT usr.id, usr.first_name, usr.last_name, usr.email_address, " +
                    "acc.user_name, acc_usr.role_name, acc_usr.account_id " +
                    "FROM accounts_roles AS acc_usr, users AS usr " +
                    "INNER JOIN accounts AS acc " +
                    "ON usr.id = acc.id " +
                    "WHERE usr.first_name LIKE %?1% OR usr.last_name LIKE %?1% OR usr.email_address LIKE %?1% " +
                    "OR acc_usr.role_name=?1 OR acc.user_name=?1",
            nativeQuery = true)
    List<User> findByFilter(String filter);
}
