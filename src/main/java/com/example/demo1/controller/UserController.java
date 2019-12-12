package com.example.demo1.controller;

import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import com.example.demo1.util.StatusResponse;
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

import static com.example.demo1.model.StatusChanger.changeToAway;
import static com.example.demo1.util.ValidationUtil.assureIdConsistent;


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
    public List<User> getAll(@RequestParam(value = "enabled", required = false) StatusOfEnable enabled,
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
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = service.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StatusResponse> changeStatus(@PathVariable("id") Integer id, @RequestParam("enabled") StatusOfEnable current) {
        StatusResponse sr = new StatusResponse();
            Timestamp updateTime = new Timestamp((new Date()).getTime());
            try {
                User user = service.getById(id);
                StatusOfEnable old = user.getEnabled();
                user.setEnabled(current);
                user.setStatusTimestamp(updateTime);
                service.update(user);
                if(current.equals(StatusOfEnable.ONLINE)){
                    changeToAway(user);
                }
                sr.setId(id);
                sr.setOldStatus(old);
                sr.setCurrentStatus(current);
                return new ResponseEntity<>(sr, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity(sr, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


}
