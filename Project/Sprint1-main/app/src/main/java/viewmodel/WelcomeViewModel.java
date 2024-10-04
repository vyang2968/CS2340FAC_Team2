package viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isStartClicked = new MutableLiveData<>();

    public LiveData<Boolean> getIsStartClicked() {
        return isStartClicked;
    }

    public void onStartClicked() {
        isStartClicked.setValue(true);
    }

    public void onQuitClicked() {
        isStartClicked.setValue(false);
    }
}