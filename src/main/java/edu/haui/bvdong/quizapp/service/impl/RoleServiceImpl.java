package edu.haui.bvdong.quizapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.haui.bvdong.quizapp.model.Role;
import edu.haui.bvdong.quizapp.repository.RoleRepository;
import edu.haui.bvdong.quizapp.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }
}
