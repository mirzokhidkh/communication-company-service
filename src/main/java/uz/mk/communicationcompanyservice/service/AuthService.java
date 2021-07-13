package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.Simcard;
import uz.mk.communicationcompanyservice.entity.Turniket;
import uz.mk.communicationcompanyservice.entity.User;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.LoginDto;
import uz.mk.communicationcompanyservice.payload.RegisterDto;
import uz.mk.communicationcompanyservice.repository.RoleRepository;
import uz.mk.communicationcompanyservice.repository.SimcardRepository;
import uz.mk.communicationcompanyservice.repository.UserRepository;
import uz.mk.communicationcompanyservice.security.JwtProvider;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    final AuthenticationManager authenticationManager;

    final JwtProvider jwtProvider;

    final SimcardRepository simcardRepository;

    @Autowired
    public AuthService(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RoleRepository roleRepository, SimcardRepository simcardRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.simcardRepository = simcardRepository;
    }

    public ApiResponse register(RegisterDto registerDto) {

        boolean existsByUsername = userRepository.existsByUsername(registerDto.getUsername());
        if (existsByUsername) {
            return new ApiResponse("User with such a username already exists", false);
        }

        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        User principalUser = (User) contextHolder.get("principalUser");
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        Set<Integer> roleIds = registerDto.getRoleIds();
        Set<Role> roleSet = roleRepository.findAllByIdIn(roleIds);
        boolean existRoleStaff = CommonUtils.isExistsAuthority(roleSet, RoleName.ROLE_STAFF);


        boolean isDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_DIRECTOR) &&
                CommonUtils.isExistsAuthority(roleSet, "MANAGER");

        boolean isManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_MANAGER) &&
                existRoleStaff;

        if (!(isDirectorAuthority || isManagerAuthority)) {
            return new ApiResponse("You don't have the authority to add staff", false);
        }

        User user = new User();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setUsername(registerDto.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setPassportId(registerDto.getPassportId());
        user.setStatus(true);
        user.setRole(roleSet);


        if (existRoleStaff) {
            Turniket turniket = new Turniket();
            turniket.setStaff(user);
            turniket.setStatus(true);

            user.setTurniket(turniket);
        }


        User savedUser = userRepository.save(user);
        return new ApiResponse("Successfully registered", true, savedUser);
    }

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());
            return new ApiResponse("Token", true, token);

        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Username or password incorrect", true);
        }

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(username + " not found");
    }


    public UserDetails loadSimCardByBackNumberFromSimCard(String simCardBackNumber) {
        return simcardRepository.findBySimCardBackNumber(simCardBackNumber)
                .orElseThrow(() -> new UsernameNotFoundException(simCardBackNumber + " not found"));
    }


    public UserDetails loadUserByClientNameFromSimCard(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return simcardRepository.findByClient(optionalUser.get())
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
