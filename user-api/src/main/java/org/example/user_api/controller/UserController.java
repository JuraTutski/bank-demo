package org.example.user_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.user_api.entity.User;
import org.example.user_api.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @Valid @RequestBody User updateUser){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updateUser.getAge() != null) {
            user.setAge(updateUser.getAge());
        }

        if (updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }

        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}