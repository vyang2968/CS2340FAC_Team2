package com.example.sprintproject.service;



import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sprintproject.model.User;
import com.example.sprintproject.repository.UserRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

public class UserService {
    private final UserRepositoryImpl userRepository;
    private static UserService instance;
    private User user;
    private MutableLiveData<Boolean> hasCurrentUser;
    private static final String TAG = "UserService";

    private UserService() {
        this.userRepository = new UserRepositoryImpl();
        this.user = new User();
        this.hasCurrentUser = new MutableLiveData<>();
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

    public void getUser(String id, DataCallback<User> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        userRepository.getUserById(id, callback);
    }

    public void getUserByEmail(String email, DataCallback<User> callback) {
        if (email == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (email.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        userRepository.getUserByEmail(email, callback);
    }

    public void setCurrentUser(User user) {
        Log.i(TAG, "setCurrUser CALLED");
        this.user = user;
        this.hasCurrentUser.setValue(true);
    }

    public User getCurrentUser() {
        Log.i(TAG, "getCurrUser CALLED");
        return this.user;
    }

    public MutableLiveData<Boolean> getHasCurrentUser() {
        return hasCurrentUser;
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
