package co.edu.udea.kplus1.appuntesmobile.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class ApunteViewModel extends ViewModel {
    private MutableLiveData<Apunte> apunte = new MutableLiveData<>();

    public void set(Apunte newData) {
        apunte.setValue(newData);
    }

    public LiveData<Apunte> get() {
        return apunte;
    }

    public ApunteViewModel() {

    }
}
