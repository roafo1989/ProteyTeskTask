package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.service.UserServiceImpl;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.example.demo1.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    static final String REST_URL = "/users";

    private UserServiceImpl service;
    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/get.by.id/{id}")
    public User getById(@PathVariable int id) {
        return service.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.deleteUser(id);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        assureIdConsistent(user,id);
        service.updateUser(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.service.createUser(user);
        return new ResponseEntity<>(user,headers,HttpStatus.CREATED);
    }

    @PutMapping(value = "change.status/{id}")
    public ResponseEntity<StatusResponse> changeStatus(@PathVariable("id") Integer id, @RequestParam("enabled") String status) {
        StatusResponse statusResponse = service.changeStatus(id, status);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
}
