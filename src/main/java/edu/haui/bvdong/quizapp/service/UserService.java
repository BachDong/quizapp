package edu.haui.bvdong.quizapp.service;


import edu.haui.bvdong.quizapp.dto.UserDto;
import edu.haui.bvdong.quizapp.exception.PasswordMismatchException;
import edu.haui.bvdong.quizapp.exception.UserExistException;
import edu.haui.bvdong.quizapp.model.User;

public interface UserService {
    User add(UserDto userDto) throws UserExistException, PasswordMismatchException;
    User findByUserName(String userName);
}
