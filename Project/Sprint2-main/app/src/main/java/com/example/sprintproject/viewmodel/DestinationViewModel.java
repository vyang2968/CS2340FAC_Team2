package com.example.sprintproject.viewmodel;

import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.User;
import com.example.sprintproject.repository.DestinationRepositoryImpl;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class DestinationViewModel extends ViewModel {
    private static final String TAG = "DestinationViewModel";
    private UserService userService;
    private FirebaseUser currUser;

    public DestinationViewModel() {
        this.userService = UserService.getInstance();
        this.currUser = AuthService.getInstance().getUser();
    }

    public void setStartDate(EditText startDate) {
        // validation

    }
}
