package uz.mk.communicationcompanyservice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.User;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.repository.RoleRepository;
import uz.mk.communicationcompanyservice.repository.UserRepository;

import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            User user = new User(
                    "John",
                    "Doe",
                    "john",
                    passwordEncoder.encode("123")
                    );
            Role director = roleRepository.findByRoleName(RoleName.ROLE_DIRECTOR);
            user.setRole(Collections.singleton(director));
            user.setEnabled(true);
            userRepository.save(user);
        }
    }
}
