package com.example.sprintproject.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityTravelCommunityBinding;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.utils.TravelPostListener;
import com.example.sprintproject.viewmodel.TravelCommunityViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TravelCommunityScreen extends NavBarScreen implements TravelPostListener {

    private TravelCommunityViewModel viewModel;
    private LinearLayout postForm;
    private LinearLayout postContainer;
    private Button createButton;
    private Button postButton;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityTravelCommunityBinding binding =
                ActivityTravelCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        viewModel =
                new ViewModelProvider(this).get(TravelCommunityViewModel.class);
        binding.setVariable(BR.travelViewModel, viewModel);
        binding.setLifecycleOwner(this);

        inflater = LayoutInflater.from(this);

        postForm = findViewById(R.id.createPostWindow);
        createButton = findViewById(R.id.createPostButton);
        postButton = findViewById(R.id.postButton);
        postContainer = findViewById(R.id.postList);

        this.postContainer.removeAllViews();
        subscribe(viewModel);
        viewModel.getTravelPosts();

        createButton.setOnClickListener(view -> toggleFormVisibility());
        postButton.setOnClickListener(view -> postTravel());
    }

    private void toggleFormVisibility() {
        if (postForm.getVisibility() == View.GONE) {
            postForm.setVisibility(View.VISIBLE);
        } else {
            postForm.setVisibility(View.GONE);
        }
    }

    private void postTravel() {
        EditText startInput = findViewById(R.id.startInput);
        EditText endInput = findViewById(R.id.endInput);
        EditText destinationInput = findViewById(R.id.destinationInput);
        EditText accommodationsInput = findViewById(R.id.accommodationsInput);
        EditText reservationsInput = findViewById(R.id.reservationsInput);
        EditText notesInput = findViewById(R.id.notesInput);

        String start = startInput.getText().toString();
        String end = endInput.getText().toString();
        String destination = destinationInput.getText().toString();
        String accommodations = accommodationsInput.getText().toString();
        String reservations = reservationsInput.getText().toString();
        String notes = notesInput.getText().toString();


        try {
            viewModel.addTravelPost(start, end, destination, accommodations, reservations, notes);

            startInput.setText("");
            endInput.setText("");
            destinationInput.setText("");
            accommodationsInput.setText("");
            reservationsInput.setText("");
            notesInput.setText("");
            postForm.setVisibility(View.GONE);
        } catch (IllegalArgumentException ignored) {
            if (destination.isEmpty()
                    || accommodations.isEmpty()
                    || reservations.isEmpty()
                    || notes.isEmpty()) {
                if (destination.isEmpty()) {
                    destinationInput.setError("Must be filled in.");
                }
                if (accommodations.isEmpty()) {
                    accommodationsInput.setError("Must be filled in.");
                }
                if (reservations.isEmpty()) {
                    reservationsInput.setError("Must be filled in.");
                }
                if (notes.isEmpty()) {
                    notesInput.setError("Must be filled in.");
                }
            } else {
                startInput.setError("Start date must be mm/dd/yyyy format");
                endInput.setError("End date must be mm/dd/yyyy format");
            }
        }
    }

    private void createPostView(List<TravelPost> posts) {
        this.postContainer.removeAllViews();
        for (TravelPost post : posts) {
            observePost(post);
        }
    }

    @Override
    public void observePost(TravelPost post) {
        View postView = getLayoutInflater().inflate(R.layout.post_layout,
                postContainer, false);
        TextView usernameTextView = postView.findViewById(R.id.postUsername);
        TextView destinationTextView = postView.findViewById(R.id.postDestination);
        TextView durationTextView = postView.findViewById(R.id.postDuration);

        usernameTextView.setText(post.getNotes().getCreator().getUsername());
        destinationTextView.setText(post.getDestination());
        long diff = post.getEndDate().getTime() - post.getStartDate().getTime();
        durationTextView.setText(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " Days");

        this.postContainer.addView(postView);
    }
}