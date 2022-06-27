package edu.haui.bvdong.quizapp.service;

import edu.haui.bvdong.quizapp.model.Role;

public interface RoleService {
    Role findByAuthority(String authority);
}
