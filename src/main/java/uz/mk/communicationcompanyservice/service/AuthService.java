package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.User;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.LoginDto;
import uz.mk.communicationcompanyservice.payload.RegisterDto;
import uz.mk.communicationcompanyservice.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse register(RegisterDto registerDto) {

        boolean existsByUsername = userRepository.existsByUsername(registerDto.getUsername());
        if (existsByUsername) {
            return new ApiResponse("User with such a username already exists", false);
        }


        User user = new User();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setUsername(registerDto.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setPassportId(registerDto.getPassportId());


        user.setStatus(true);

        Set<Integer> roles = registerDto.getRoles();

        User savedUser = userRepository.save(user);
        return new ApiResponse("Successfully registered", true);
    }

    public ApiResponse login(LoginDto loginDto) {

        return new ApiResponse("User entered", true);

    }


        @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new UsernameNotFoundException(username+" not found");
    }
}
