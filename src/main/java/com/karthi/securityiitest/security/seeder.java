package com.karthi.securityiitest.security;

import com.karthi.securityiitest.model.Role;
import com.karthi.securityiitest.model.User;
import com.karthi.securityiitest.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class seeder implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        boolean superAdminExists = userRepo.existsByRole(Role.ROLE_SUPER_ADMIN);

        if(!superAdminExists){
            User superAdmin = User.builder()
                    .username("superadmin")
                    .password(passwordEncoder.encode("123456"))
                    .enabled(true)
                    .role(Role.ROLE_SUPER_ADMIN)
                    .createdBy("SYSTEM")
                    .build();
            userRepo.save(superAdmin);
        }
        log.info("========================================");
        log.info("SUPER_ADMIN created — username: superadmin | password: 123456");
        log.info("========================================");

    }
}
