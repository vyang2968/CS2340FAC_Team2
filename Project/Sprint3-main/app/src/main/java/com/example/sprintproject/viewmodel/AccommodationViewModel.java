package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccommodationViewModel extends ViewModel implements LogSource {

    private static final String TAG = "AccommodationViewModel";

    private final MutableLiveData<List<Accommodation>> accommodations;

    public AccommodationViewModel() {
        this.accommodations = new MutableLiveData<>();
    }

    public void addAccommodation(String checkInTime, String checkOutTime,
                                 String location, int rooms, String roomType) {
        Accommodation accommodation = new Accommodation();

        accommodation.setCheckInTime(parseDate(checkInTime));
        accommodation.setCheckInTime(parseDate(checkOutTime));
        accommodation.setLocation(location);
        accommodation.setNumRooms(rooms);
        accommodation.setRoomType(roomType);

        List<Accommodation> list = accommodations.getValue();

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(accommodation);

        accommodations.setValue(list);
    }

    private Date parseDate(String dateText) {
        Log.i(TAG, "parsing " + dateText);
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date = format.parse(dateText);
            Log.i(TAG, "parseDate:success");
            return date;
        } catch (ParseException error) {
            Log.d(TAG, "date could not be parsed");
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }
}
