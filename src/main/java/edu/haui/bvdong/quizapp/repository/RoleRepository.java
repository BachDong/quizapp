package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByAuthority(String authority);
}
