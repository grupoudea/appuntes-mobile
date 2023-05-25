package co.edu.udea.kplus1.appuntesmobile.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class MateriasViewModel extends ViewModel {

    private MutableLiveData<Materia> materia = new MutableLiveData<>();

    public void set(Materia newData) {
        materia.setValue(newData);
    }

    public LiveData<Materia> get() {
        return materia;
    }

    public MateriasViewModel() {

    }
}
