package com.example.demo1.service;

import com.example.demo1.model.User;
import com.example.demo1.util.StatusResponse;
import java.util.List;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    User createUser(User user);
    void updateUser(User user);
    StatusResponse changeStatus(int id, String newStatus);
    void updateStatus();
    void deleteUser(int id);
}
