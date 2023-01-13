package com.pps.controller.domain.user;

public interface UserService {

    void createUser(UserDto user);

    UserEntity getUserByEmail(String email);
}
