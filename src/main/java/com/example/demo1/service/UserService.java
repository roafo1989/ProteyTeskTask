package com.example.demo1.service;

import com.example.demo1.model.User;
import com.example.demo1.util.StatusResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Integer createUser(User user);
    Optional<User> getUserById(int id);
    Optional<StatusResponse> changeStatus(int id, String newStatus);
    void updateStatus();

    //optional methods
    void deleteUser(int id);
    void updateUser(User user);
    List<User> getAll();
}
