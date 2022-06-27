package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.User;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
}
