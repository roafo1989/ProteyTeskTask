package com.example.demo1.controller;

import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.service.UserService;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static com.example.demo1.model.StatusOfEnable.AWAY;
import static com.example.demo1.util.StatusChanger.changeToAway;
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
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/get.by.id/{id}")
    public User getById(@PathVariable int id) {
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user,id);
        service.update(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.service.create(user);
        return new ResponseEntity<>(user,headers,HttpStatus.CREATED);

       /* User created = service.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);*/

    }

    @PutMapping(value = "change.status/{id}")
    public ResponseEntity<StatusResponse> changeStatus(@PathVariable("id") Integer id, @RequestParam("enabled") String status) {
        StatusOfEnable current = StatusOfEnable.values()[Integer.parseInt(status)];
        StatusResponse statusResponse = new StatusResponse();
        try {
            User user = service.getById(id);
            statusResponse.setOldStatus(user.getEnabled());
            statusResponse.setId(id);
            statusResponse.setCurrentStatus(current);
            service.changeStatus(user,current);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
}
