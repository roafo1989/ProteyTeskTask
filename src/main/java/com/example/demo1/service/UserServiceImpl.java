package com.example.demo1.service;

import com.example.demo1.dao.UserRepo;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;

import static com.example.demo1.util.ValidationUtil.checkNotFoundWithId;


@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public Integer createUser(User user){
        Assert.notNull(user,"user must be not null");
        if(user.getOnlineTime() == null){
            user.setOnlineTime(new Date());
        }
        if(user.getEnabled() == null){
            System.out.println("if from service");
            user.setEnabled(StatusOfEnable.ONLINE);
        }
        return userRepo.save(user).getId();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<StatusResponse> changeStatus(int id, String newStatus){
        StatusOfEnable current = StatusOfEnable.values()[Integer.parseInt(newStatus)];
        Optional<User> userOptional = userRepo.findById(id);
        Optional<StatusResponse> status = Optional.empty();

        if(userOptional.isPresent()){
            User user = userOptional.get();
            status = Optional.of(new StatusResponse(id,user.getEnabled(),current));
            user.setEnabled(current);
            user.setOnlineTime(new Date());
            userRepo.saveAndFlush(user);
        }
        return status;
    }
    @Override
    public void updateStatus() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        Date calTime = cal.getTime();
        userRepo.updateStatus(calTime);
    }


    //optional methods
    @Override
    public void updateUser(User user){
        Assert.notNull(user,"user must be not null");
        user.setOnlineTime(new Date());
        userRepo.saveAndFlush(user);
    }
    @Override
    public void deleteUser(int id){
        checkNotFoundWithId(userRepo.delete(id),id);
    }
    @Override
    public List<User> getAll(){
        return userRepo.findAll();
    }

}
