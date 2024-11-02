package com.example.sprintproject.service;



import com.example.sprintproject.model.User;
import com.example.sprintproject.repository.UserRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

public class UserService {
    private UserRepositoryImpl userRepository;
    private static UserService instance;
    private User user;
    private final String TAG = "userService";

    public String returnTag(){
        return TAG;
    }

    private UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Task<Void> addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        } else if (user.getId().isEmpty()) {
            throw new IllegalArgumentException("user id cannot be empty");
        }

        return userRepository.addUser(user);
    }

    public User getUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        userRepository.getUserById(id, new DataCallback<User>() {
            @Override
            public void onSuccess(User result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
        return null;
    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        return user;
    }

    public Task<Void> updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null");
        } else if (user.getId().isEmpty()) {
            throw new IllegalArgumentException("user id cannot be empty");
        }

        return userRepository.updateUser(user);
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
