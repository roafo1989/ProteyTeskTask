package com.example.demo1.service;

import com.example.demo1.dao.UserRepo;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.sql.Timestamp;
import java.util.List;

import static com.example.demo1.util.ValidationUtil.checkNotFoundWithId;


@Service
public class UserService {

    private UserRepo userRepo;
    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User create(User user){
        Assert.notNull(user,"user must be not null");
        return userRepo.save(user);
    }

    public void update(User user){
        Assert.notNull(user,"user must be not null");
        userRepo.save(user);
    }

    public User getById(int id){
        return checkNotFoundWithId(userRepo.findById(id).orElse(null),id);
    }

    public void delete(int id){
        checkNotFoundWithId(userRepo.delete(id),id);
    }

    public List<User> findByEnabledAndStatusTimestampAfter (StatusOfEnable enabled, Timestamp statusTimestamp){
        return userRepo.findByEnabledAndStatusTimestampAfter(enabled, statusTimestamp);
    }
    public List<User> findByEnabled (StatusOfEnable enabled){
        return userRepo.findByEnabled(enabled);
    }
    public List<User> findByStatusTimestampAfter (Timestamp statusTimestamp){
        return userRepo.findByStatusTimestampAfter(statusTimestamp);
    }


}
