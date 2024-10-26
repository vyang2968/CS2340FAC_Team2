package com.example.sprintproject.viewmodel;

import android.util.Log;

public interface LogSource {
    String getTag();

    default int logInfo(String message) {
        return Log.i(getTag(), message);
    }

    default int logDebug(String message) {
        return Log.d(getTag(), message);
    }
}
