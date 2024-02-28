package com.project.bookmarkservice.bookmarkservice.services;

import com.project.bookmarkservice.bookmarkservice.models.Book;
import com.project.bookmarkservice.bookmarkservice.models.User;
import com.project.bookmarkservice.bookmarkservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User RegisterUser(String email){
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User loginUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        optionalUser.orElseThrow();
        return optionalUser.get();
    }

    public User addBook(String userId, Book book) {
        Optional<User> optionalUser = userRepository.findByEmail(userId);
        optionalUser.orElseThrow();
        User user = optionalUser.get();
        List<Book> books = user.getUserBooks();
        books.add(book);
        user.setUserBooks(books);

        return user;
    }

    public User deleteUserBook(Long userId, Long bookId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        optionalUser.orElseThrow();
        User user = optionalUser.get();

        List<Book> books = user.getUserBooks();
        user.setUserBooks(books.stream().filter(book -> !Objects.equals(book.getId(), bookId)).toList());
        return user;
    }
}
