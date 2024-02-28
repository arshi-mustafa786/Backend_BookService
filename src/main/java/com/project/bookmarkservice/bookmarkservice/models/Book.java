package com.project.bookmarkservice.bookmarkservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "books")
@Getter
@Setter
public class Book extends Base{
    private String name;
}
