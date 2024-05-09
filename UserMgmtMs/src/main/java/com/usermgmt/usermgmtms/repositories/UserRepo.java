package com.usermgmt.usermgmtms.repositories;

import com.usermgmt.usermgmtms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    User findByUserTypeAndUserID(User.UserType userType, Long id);

    List<User> findByUserTypeNot(User.UserType userType);
}
