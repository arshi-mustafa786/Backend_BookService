package com.project.bookmarkservice.bookmarkservice.controllers;

import com.project.bookmarkservice.bookmarkservice.dto.UserDto;
import com.project.bookmarkservice.bookmarkservice.models.Book;
import com.project.bookmarkservice.bookmarkservice.models.User;
import com.project.bookmarkservice.bookmarkservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController("/user")
public class UserController {
    UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody UserDto user){
        return this.userService.RegisterUser(user.getEmail());
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserDto user){
        return this.userService.loginUser(user.getEmail());
    }

    @PutMapping("/{userId}/book")
    public User addBook(@RequestBody Book book, @PathVariable("userId") String userId){
        return this.userService.addBook(userId,book);
    }

    @DeleteMapping("/{userId}/{bookId}")
    public User deleteBook(@PathVariable("bookId" ) Long bookId, @PathVariable("userId") Long userId){
        return this.userService.deleteUserBook(userId,bookId);
    }
}
