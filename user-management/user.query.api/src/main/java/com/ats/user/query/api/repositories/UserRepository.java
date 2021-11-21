package com.ats.user.query.api.repositories;

import com.ats.user.core.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT usr.id, usr.first_name, usr.last_name, usr.email_address, " +
            "acc.user_name, acc_usr.role_name, acc_usr.account_id, acc.id " +
            "FROM accounts_roles AS acc_usr, users AS usr, accounts AS acc " +
            "WHERE usr.id = acc.id and acc.id = acc_usr.account_id and acc.user_name=?1",
            nativeQuery = true)
    List<User> findByFilter(String filter);

    // TODO: Optimize query
    @Modifying
    @Query(value = "DELETE FROM result_operations WHERE result_operations.account_id=:id ;\n" +
            "DELETE FROM files WHERE files.account_id=:id ;\n" +
            "DELETE FROM accounts_roles WHERE accounts_roles.account_id=:id ;\n" +
            "DELETE FROM users WHERE users.account_id=:id ;\n" +
            "DELETE FROM accounts WHERE accounts.id=:id ;",
        nativeQuery = true)
    void deleteByUserId(String id);
}
