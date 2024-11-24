package com.example.sprintproject.view;

import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivitySpecificDestinationScreenBinding;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Note;
import com.example.sprintproject.viewmodel.SpecificDestinationViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class SpecificDestinationScreen extends AppCompatActivity {
    public static final String TAG = "SpecificDestScreen";
    public static final int ENTRIES_PER_PAGES = 7;

    private SpecificDestinationViewModel sDestViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_destination_screen);
        EdgeToEdge.enable(this);

        ActivitySpecificDestinationScreenBinding binding =
                ActivitySpecificDestinationScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sDestViewModel = new ViewModelProvider(this).get(SpecificDestinationViewModel.class);
        binding.setVariable(BR.sDestViewModel, sDestViewModel);
        binding.setLifecycleOwner(this);
        Destination destination = (Destination) getIntent().getSerializableExtra("destination");
        sDestViewModel.setDestination(destination);

        ImageButton left = findViewById(R.id.button_previous);
        ImageButton right = findViewById(R.id.button_next);
        left.setOnClickListener(view -> {
            int value = sDestViewModel.getStartingDay().getValue() - 7;
            Log.i(TAG, "left:clicked with value " + value);
            if (value >= 1) {
                sDestViewModel.setStartingDay(value);
            }
        });

        right.setOnClickListener(view -> {
            int value = sDestViewModel.getStartingDay().getValue() + 7;
            Log.i(TAG, "left:clicked with value " + value);
            if (value < sDestViewModel.getDuration()) {
                sDestViewModel.setStartingDay(value);
            }
        });

        LinearLayout dayPlansArea = findViewById(R.id.dayPlansArea);

        String startDate = sDestViewModel.getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        Date parsed = new Date();
        try {
            parsed = format.parse(startDate);
        } catch (Exception e) {
            Log.d(TAG, "could not parse date");
        }

        createSubmissionStatusObserver();

        Date finalParsed = parsed;
        sDestViewModel.getStartingDay().observe(this, startingDay -> {
            dayPlansArea.removeAllViews();
            Log.i(TAG, "populating day plans...");
            int count = startingDay;
            for (int i = startingDay; i < (startingDay + ENTRIES_PER_PAGES); i++) {
                if (count++ > sDestViewModel.getDuration() + 1) {
                    break;
                }
                String details = sDestViewModel.getDayPlanDetail(i);

                LinearLayout layout = createRow(finalParsed, i, details);
                dayPlansArea.addView(layout);
            }
        });
    }

    private LinearLayout createRow(Date startDate, int day, String details) {
        LinearLayout layout = new LinearLayout(
                new ContextThemeWrapper(this, R.style.dayPlanRow)
        );
        layout.setClickable(true);


        LinearLayout dayInfo = new LinearLayout(new ContextThemeWrapper(this, R.style.dayLayout));

        TextView dayNumber = new TextView(this);
        dayNumber.setText(String.format("Day %d", day));
        dayNumber.setTextSize(12);

        TextView date = new TextView(this);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, day - 1);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        date.setText(format.format(calendar.getTime()));
        date.setTextSize(12);

        dayInfo.addView(dayNumber);
        dayInfo.addView(date);

        TextView detailsTruncated = new TextView(new ContextThemeWrapper(this, R.style.details));
        if (details.length() >= 30) {
            detailsTruncated.setText(details.substring(0, 30).concat("..."));
        } else if (details.isEmpty()) {
            detailsTruncated.setText("No details present");
            detailsTruncated.setTypeface(null, Typeface.BOLD);
        } else {
            detailsTruncated.setText(details);
        }

        ImageButton noteButton = new ImageButton(new ContextThemeWrapper(this, R.style.noteButton));
        noteButton.setOnClickListener(view -> {
            openNotesDialog(day);
        });

        layout.setOnClickListener(view -> {
            openDetailsDialog(day, sDestViewModel.getStartDate(), detailsTruncated);
        });

        layout.addView(dayInfo);
        layout.addView(detailsTruncated);
        layout.addView(noteButton);

        return layout;
    }

    private void createSubmissionStatusObserver() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(SpecificDestinationScreen.this);
        AlertDialog status = builder2.create();
        sDestViewModel.getUpdateDestinationSuccessful()
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean bool) {
                        status.setMessage(
                                bool ? "Successfully saved!" : "Unsuccessful, please try again"
                        );
                        status.show();
                    }
                });
    }

    private void openDetailsDialog(int day, String date, TextView textArea) {
        Log.i(TAG, "dayPlan:clicked");

        boolean isOwner = sDestViewModel.getIsOwner();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format("Day %d, %s", day, date));
        String dayPlan = sDestViewModel.getDayPlanDetail(day);

        if (isOwner) {
            EditText editText = new EditText(this);
            editText.setText(dayPlan);
            builder.setView(editText);

            builder.setPositiveButton("Save", (dialogInterface, i) -> {
                Log.i(TAG, "saving details...");
                sDestViewModel.updateDetail(day, editText.getText().toString());
                textArea.setText(editText.getText().toString());
                textArea.setTypeface(Typeface.DEFAULT);
            });

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });
        } else {
            TextView textView = new TextView(this);
            textView.setText(dayPlan);
            builder.setView(textView);
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                dialogInterface.cancel();
            });
        }
        AlertDialog detail = builder.create();
        detail.show();
    }

    private void openNotesDialog(int day) {
        Log.i(TAG, "notesButton:clicked");
        List<Note> notes = sDestViewModel.getDayPlanNotes(day);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notes");

        EditText noteInput = new EditText(new ContextThemeWrapper(this, R.style.noteInput));

        LinearLayout outerContainer = createNotesDialogContent(notes, noteInput);

        builder.setView(outerContainer);

        builder.setPositiveButton("Save", (dialog, i) -> {
            String text = noteInput.getText().toString().trim();
            if (text.isEmpty()) {
                noteInput.setError("Note can't be empty");
            } else {
                noteInput.setError(null);
            }
            sDestViewModel.updateNote(day, text);
        });

        builder.setNegativeButton("Cancel", (dialog, i) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private LinearLayout createNotesDialogContent(List<Note> notes, EditText noteInput) {
        LinearLayout outerContainer = new LinearLayout(this);
        outerContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        outerContainer.setOrientation(LinearLayout.VERTICAL);

        ScrollView scroll = new ScrollView(new ContextThemeWrapper(this, R.style.notesScroll));
        LinearLayout container = new LinearLayout(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER_VERTICAL);

        if (!notes.isEmpty()) {
            for (Note note : notes) {
                LinearLayout row = new LinearLayout(
                        new ContextThemeWrapper(this, R.style.dayPlanRow)
                );

                TextView noteText = new TextView(new ContextThemeWrapper(this, R.style.note));
                noteText.setText(note.getNote());

                TextView noteCreatorText =
                        new TextView(new ContextThemeWrapper(this, R.style.noteCreator));
                noteCreatorText.setText(
                        String.format("Created by: %s", note.getCreator().getUsername()));

                row.addView(noteText);
                row.addView(noteCreatorText);

                container.addView(row);
            }
        } else {
            TextView noNotesText = new TextView(new ContextThemeWrapper(this, R.style.noNotes));
            container.addView(noNotesText);
        }

        scroll.addView(container);

        outerContainer.addView(scroll);
        outerContainer.addView(noteInput);

        return outerContainer;
    }
}
