package co.edu.udea.kplus1.appuntesmobile.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;

public class GrupoApunteViewModel extends ViewModel {

    private MutableLiveData<GrupoApunte> grupoApunte = new MutableLiveData<>();

    public void set(GrupoApunte newData) {
        grupoApunte.setValue(newData);
    }

    public LiveData<GrupoApunte> get() {
        return grupoApunte;
    }

    public GrupoApunteViewModel() {

    }
}
