package uz.mk.communicationcompanyservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mk.communicationcompanyservice.entity.Role;
import uz.mk.communicationcompanyservice.entity.User;
import uz.mk.communicationcompanyservice.entity.enums.RoleName;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.payload.UserDto;
import uz.mk.communicationcompanyservice.repository.RoleRepository;
import uz.mk.communicationcompanyservice.repository.TurniketRepository;
import uz.mk.communicationcompanyservice.repository.UserRepository;
import uz.mk.communicationcompanyservice.utils.CommonUtils;

import java.util.*;

@Service
public class StaffService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TurniketRepository turniketRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResponse editById(UUID id, UserDto userDto) {
        boolean existsByUsername = userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id);
        if (existsByUsername) {
            return new ApiResponse("User with such a username already exists", false);
        }
        Set<Integer> roleIds = userDto.getRoleIds();
        Optional<User> optionalUser = userRepository.findById(id);
        Set<Role> roleSet = roleRepository.findAllByIdIn(roleIds);

        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        User principalUser = (User) contextHolder.get("principalUser");
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_DIRECTOR) &&
                CommonUtils.isExistsAuthority(roleSet, RoleName.ROLE_STAFF);
        boolean isManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_MANAGER) &&
                CommonUtils.isExistsAuthority(roleSet, RoleName.ROLE_STAFF);
        boolean isStaffAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_STAFF) &&
                CommonUtils.isExistsAuthority(roleSet, RoleName.ROLE_STAFF);

        if (!(isDirectorAuthority || isManagerAuthority || isStaffAuthority)) {
            return new ApiResponse("You don't have the authority to edit staff", false);
        }


        User user = optionalUser.get();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setUsername(userDto.getUsername());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPassportId(userDto.getPassportId());
        user.setStatus(userDto.isStatus());


        user.setRole(roleSet);
        User savedUser = userRepository.save(user);
        return new ApiResponse("Staff edited", true, savedUser);
    }

    public ApiResponse deleteById(UUID id) {
        Map<String, Object> contextHolder = CommonUtils.getPrincipalAndRoleFromSecurityContextHolder();
        User principalUser = (User) contextHolder.get("principalUser");
        Set<Role> principalUserRoles = (Set<Role>) contextHolder.get("principalUserRoles");

        boolean isDirectorAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_DIRECTOR);
        boolean isManagerAuthority = CommonUtils.isExistsAuthority(principalUserRoles, RoleName.ROLE_BRANCH_MANAGER);
        if (!(isDirectorAuthority || isManagerAuthority)) {
            return new ApiResponse("You don't have the authority to delete staff", false);
        }


        try {
            Optional<User> optionalUser = userRepository.findById(id);
            User user = optionalUser.get();
            user.setEnabled(false);
            user.setStatus(false);
            userRepository.save(user);
            return new ApiResponse("Staff disabled", true);
        } catch (Exception e) {
            return new ApiResponse("Staff not found", false);
        }

    }
}
