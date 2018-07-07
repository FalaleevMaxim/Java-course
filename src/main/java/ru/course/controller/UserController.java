package ru.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.course.model.User;
import ru.course.service.UsersStorage;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UsersStorage usersStorage;

    @Autowired
    public UserController(UsersStorage usersStorage) {
        this.usersStorage = usersStorage;
    }

    @RequestMapping("/list")
    public List<User> getUsers(){
        return usersStorage.all();
    }

    @RequestMapping("/{id}")
    public User getUser(@PathVariable int id){
        return usersStorage.get(id);
    }

    @RequestMapping("/save/{name}")
    public int addUser(@PathVariable String name){
        return usersStorage.add(name);
    }
}
