package com.example.sprintproject.view;

import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivitySpecificDestinationScreenBinding;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.viewmodel.SpecificDestinationViewModel;
import com.google.android.material.resources.TextAppearance;

import java.net.ContentHandler;
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
                String details = sDestViewModel.getDayPlan(i);

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
            openDetailsDialog();
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
            detailsTruncated.setText(details.concat("..."));
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

    private void openDetailsDialog() {
        Log.i(TAG, "dayPlan:clicked");
    }

    private void openNotesDialog() {
        Log.i(TAG, "notesButton:clicked");
    }
}
