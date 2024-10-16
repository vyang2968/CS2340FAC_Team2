package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.User;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;


public interface UserRepository {
    Task<Void> addUser(User user);
    void getUserbyId(String id, DataCallback<User> callback);
    Task<Void> updateUser(User user);
    Task<Void> deleteUser(String id);
}
