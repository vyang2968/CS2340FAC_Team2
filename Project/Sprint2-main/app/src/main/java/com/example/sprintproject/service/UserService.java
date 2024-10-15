package com.example.sprintproject.service;

import com.example.sprintproject.model.User;
import com.example.sprintproject.repository.UserRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;

public class UserService {
    private UserRepositoryImpl userRepository;
    private static UserService instance;

    private UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        } else if (user.getId().isEmpty()) {
            throw new IllegalArgumentException("user id cannot be empty");
        }

        return userRepository.addUser(user).isSuccessful();
    }

    public void getUser(String id, DataCallback<User> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        userRepository.getUserbyId(id, callback);
    }

    public boolean updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        } else if (user.getId().isEmpty()) {
            throw new IllegalArgumentException("user id cannot be empty");
        }

        return userRepository.updateUser(user).isSuccessful();
    }

    public boolean deleteUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        return userRepository.deleteUser(id).isSuccessful();
    }
}
