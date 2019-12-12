package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import com.example.demo1.util.StatusResponse;
import com.example.demo1.util.OuterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static com.example.demo1.util.ValidationUtil.assureIdConsistent;
import static com.example.demo1.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    static final String REST_URL = "/users";

    private UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll(@RequestParam(value = "enabled", required = false) Boolean enabled,
                             @RequestParam(value = "id", required = false) Integer id){
        if(id != null){
            Timestamp timestamp = new Timestamp(id);
            if(enabled != null){
                return service.findByEnabledAndStatusTimestampAfter(enabled,timestamp);
            }
            return service.findByStatusTimestampAfter(timestamp);
        } else if(enabled != null){
            return service.findByEnabled(enabled);
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user,id);
        service.update(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Callable<ResponseEntity> create(@RequestBody User user) {
        return () -> {
            try {
                User u = service.create(user);
                return new ResponseEntity<>(u.getId(), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

    @PutMapping(value = "/{id}")
    public Callable<ResponseEntity> changeStatus(@PathVariable("id") Integer id, @RequestParam("enabled") Boolean current) {
        return () -> {
            Timestamp updateTime = new Timestamp((new Date()).getTime());
            OuterRequest.sendOuterRequest();
            try {
                User user = service.getById(id);
                boolean old = user.isEnabled();
                user.setEnabled(current);
                user.setStatusTimestamp(updateTime);
                service.update(user);
                StatusResponse sr = new StatusResponse()
                        .setId(id)
                        .setOldStatus(old)
                        .setCurrentStatus(current);
                return new ResponseEntity<>(sr, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
