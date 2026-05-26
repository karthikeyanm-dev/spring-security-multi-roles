package com.karthi.securityiitest.service;

import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.model.User;
import com.karthi.securityiitest.repo.UserRepo;
import com.karthi.securityiitest.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username).orElseThrow(()->{
            return new UsernameNotFoundException("User not found with username: "+username);
        });

        return new UserPrincipal(user);
    }

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public List<User> findAllUsersByRole(Role role){
        return userRepo.findAllByRole(role);
    }
}
