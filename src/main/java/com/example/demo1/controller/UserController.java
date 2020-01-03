package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.service.UserServiceImpl;
import com.example.demo1.util.Handlers.ApiResponse;
import com.example.demo1.util.Handlers.EntityNotFoundException;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.example.demo1.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserServiceImpl service;
    @Autowired
    public UserController(UserServiceImpl service) {
        this.service = service;
    }


    @GetMapping("/get.by.id/{id}")
    public ApiResponse<User> getById(@PathVariable Integer id) throws EntityNotFoundException {
        Optional<User> user = service.getUserById(id);
        return user.map(value -> new ApiResponse<>(HttpStatus.OK, user.get()))
                .orElseThrow(() -> new EntityNotFoundException(User.class, "id", id.toString()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Integer> create(@RequestBody User user) {
        return new ApiResponse<>(HttpStatus.CREATED, service.createUser(user));
    }

    @PutMapping(value = "change.status/{id}")
    public ApiResponse<StatusResponse> changeStatus(@PathVariable("id") Integer id, @RequestParam("enabled") String status) throws EntityNotFoundException {
        Optional<StatusResponse> statusResponse = service.changeStatus(id, status);
        return statusResponse.map(value -> new ApiResponse<>(HttpStatus.OK, statusResponse.get()))
                .orElseThrow(() -> new EntityNotFoundException(StatusResponse.class, "Status", status.toString()));
    }


//optional methods
    @GetMapping
    public ApiResponse<List<User>> getAll(){
        return new ApiResponse<>(HttpStatus.OK, service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void delete(@PathVariable int id) {
        service.deleteUser(id);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<User> update(@RequestBody User user, @PathVariable int id) throws EntityNotFoundException {
        assureIdConsistent(user,id);
        service.updateUser(user);
        return getById(id);
    }
}
