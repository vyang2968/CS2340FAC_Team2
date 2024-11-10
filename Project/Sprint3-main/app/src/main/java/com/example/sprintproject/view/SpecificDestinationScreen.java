package com.example.sprintproject.view;

import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.example.sprintproject.viewmodel.SpecificDestinationViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
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
        layout.setOnClickListener(view -> {
            openDetailsDialog(day, sDestViewModel.getStartDate());
        });

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
            openNotesDialog();
        });
        layout.addView(dayInfo);
        layout.addView(detailsTruncated);
        layout.addView(noteButton);

        return layout;
    }

    private void openDetailsDialog(int day, String date) {
        Log.i(TAG, "dayPlan:clicked");

        boolean isOwner = sDestViewModel.getIsOwner();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(String.format("Day %d, %s", day, date));
        String dayPlan = sDestViewModel.getDayPlanDetail(day);

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
                        sDestViewModel.getUpdateDestinationSuccessful().removeObserver(this);
                    }
                });

        if (isOwner) {
            EditText editText = new EditText(this);
            editText.setText(dayPlan);
            builder.setView(editText);

            builder.setPositiveButton("Save", (dialogInterface, i) -> {
                Log.i(TAG, "saving details...");
                sDestViewModel.updateDetail(day, editText.getText().toString());
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

    private void openNotesDialog() {
        Log.i(TAG, "notesButton:clicked");
    }
}
