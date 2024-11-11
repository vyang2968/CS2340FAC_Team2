package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.service.AccommodationService;
import com.example.sprintproject.utils.DataCallback;
import com.example.sprintproject.utils.LogSource;
import com.example.sprintproject.utils.PlannableSortMethod;
import com.example.sprintproject.utils.SortMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AccommodationViewModel extends ViewModel implements LogSource {

    private static final String TAG = "AccommodationViewModel";

    private final MutableLiveData<List<Accommodation>> accommodations;
    private final SimpleDateFormat dateFormat;
    // Changeable Behavior
    private MutableLiveData<SortMethod<Accommodation>> sortMethod;

    public AccommodationViewModel() {
        this.accommodations = new MutableLiveData<>();
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        this.sortMethod = new MutableLiveData<>(new PlannableSortMethod<>());
    }

    public MutableLiveData<List<Accommodation>> getAccommodations() {
        return accommodations;
    }

    public void addAccommodation(String checkInTime, String checkOutTime,
                                 String location, int rooms, String roomType) {
        Accommodation accommodation = new Accommodation();

        accommodation.setCheckInTime(parseDate(checkInTime));
        accommodation.setCheckOutTime(parseDate(checkOutTime));
        accommodation.setLocation(location);
        accommodation.setNumRooms(rooms);
        accommodation.setRoomTypeFromString(roomType);

        List<Accommodation> list = accommodations.getValue();

        if (list == null) {
            list = new ArrayList<>();
        }

        list.add(accommodation);

        if (sortMethod.getValue() != null) {
            sortMethod.getValue().sortList(list);
        }

        accommodations.setValue(list);

        AccommodationService.getInstance().addAccommodation(accommodation)
            .addOnCompleteListener(bool -> {
                if (bool.isSuccessful()) {
                    Log.i(TAG, "addAccommodation:success");
                } else {
                    Log.i(TAG, "addAccommodation:fail");
                }
            });
    }

    public void queryForAccommodations() {
        accommodations.setValue(new ArrayList<>());
        AccommodationService.getInstance()
            .getAllAccommodations(new DataCallback<List<Accommodation>>() {
                @Override
                public void onSuccess(List<Accommodation> result) {
                    Log.i(TAG, "getAccommodations:success");
                    List<Accommodation> sortedAccommodations = new ArrayList<>(result);

                    // Apply current sort method
                    if (sortMethod.getValue() != null) {
                        sortMethod.getValue().sortList(sortedAccommodations);
                    }

                    accommodations.setValue(sortedAccommodations);
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "getDestinations:failed");
                    accommodations.setValue(new ArrayList<>());
                }
            });
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }

    public boolean isPastReservation(Date checkOutDate) {
        if (checkOutDate == null) {
            return false;
        }
        return checkOutDate.before(new Date());
    }

    private Date parseDate(String dateText) {
        Log.i(TAG, "parsing " + dateText);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException error) {
            Log.d(TAG, "date could not be parsed");
            throw new IllegalArgumentException(error);
        }
    }

    public void setSortMethod(SortMethod<Accommodation> sortMethod) {
        this.sortMethod.setValue(sortMethod);

        List<Accommodation> currentList = accommodations.getValue();
        if (currentList != null && sortMethod != null) {
            sortMethod.sortList(currentList);
            accommodations.setValue(currentList);
        }
    }

    public SortMethod<Accommodation> getSortMethod() {
        return this.sortMethod.getValue();
    }

    @Override
    public String getTag() {
        return TAG;
    }
}
