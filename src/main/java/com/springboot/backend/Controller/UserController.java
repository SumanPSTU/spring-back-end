package com.springboot.backend.Controller;

import com.springboot.backend.Model.User;
import com.springboot.backend.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
//@CrossOrigin("http://localhost:5173")
public class UserController {


    private final UserRepository repository;
    public UserController(UserRepository repository){
        this.repository = repository;
    }


    // add user
    @PostMapping("/user")
    String  newUser(@RequestBody User user){
        repository.save(user);
        return "user inserted";
    }

    // get all user
    @GetMapping("/users")
    List<User> getAllUser(){
        return  repository.findAll();
    }

    //update user
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userData) {
        return repository.findById(id).map(user -> {
            user.setUserName(userData.getUserName());
            user.setEmail(userData.getEmail());
            user.setPhone(userData.getPhone());
            user.setFaculty(userData.getFaculty());
            repository.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }



}
