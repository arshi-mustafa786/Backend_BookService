package com.project.bookmarkservice.bookmarkservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "user")
@Getter
@Setter
public class User extends Base{
    private String email;
    private String password;

    @ManyToMany
    private List<Book> userBooks;

}
