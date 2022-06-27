package edu.haui.bvdong.quizapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.common.UserPermissionConstants;
import edu.haui.bvdong.quizapp.dto.UserDto;
import edu.haui.bvdong.quizapp.exception.PasswordMismatchException;
import edu.haui.bvdong.quizapp.exception.UserExistException;
import edu.haui.bvdong.quizapp.model.Role;
import edu.haui.bvdong.quizapp.model.User;
import edu.haui.bvdong.quizapp.repository.RoleRepository;
import edu.haui.bvdong.quizapp.repository.UserRepository;
import edu.haui.bvdong.quizapp.service.RoleService;
import edu.haui.bvdong.quizapp.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User add(UserDto userDto) throws UserExistException, PasswordMismatchException {
        if(!userDto.getPassword().equals(userDto.getRePassword())){
            throw new PasswordMismatchException("Password mismatch");
        }

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByAuthority(UserPermissionConstants.ROLE_MEMBER));
        user.setRoles(roles);

        if(findByUserName(userDto.getUserName())!=null){
            throw new UserExistException("Username has been taken");
        }else{
           return userRepository.save(user);
        }

    }

    @Override
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
