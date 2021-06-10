package com.maybank.assessment.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.assessment.entity.User;
import com.maybank.assessment.exception.ResourceNotFoundException;
import com.maybank.assessment.service.ITodoService;

@RestController
public class UserController {
    
    @Autowired
    @Qualifier("maybankTodoService")
    ITodoService todoService;
    
    /**
     * @return list of user
     */
    @GetMapping("/users")
    public List<User> findAllUsers() {
        
        todoService.writeRequestResponseToFile("GET", ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        List<User> userList = todoService.findAllUsers();

        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(userList));

        return userList;
    }
     
    /**
     * @param id
     * @return User
     */
    @GetMapping("/users/{id}")
    public User findUser(@PathVariable int id) {
        
        todoService.writeRequestResponseToFile("GET",
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUriString());

        User user = todoService.findUsersById(id);

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + id);
        }
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(user));
        
        return user;
    }
    
    /**
     * @param user
     * @return uri path at header
     */
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        
        todoService.writeRequestResponseToFile("POST",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString() + todoService.convertToJSON(user));

        user = todoService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
                .toUri();
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(user));

        return  ResponseEntity.created(location).build();

    }

    /**
     * @param id
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        
        todoService.writeRequestResponseToFile("DELETE",
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUriString());

        User user = todoService.findUsersById(id);

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + id);
        }
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(user));

        todoService.deleteUser(user);
    }
   

}
