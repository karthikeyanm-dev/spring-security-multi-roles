package com.karthi.securityiitest.repo;

import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByRole(Role role);
    boolean existsByUsername(String username);

    List<User> findAllByRole(Role role);
}
