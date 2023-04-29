package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFormFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaPensum;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;

public class MateriasFormFragment extends Fragment {

    private MateriasFormFragmentBinding binding;
    private List<MateriaPensum> materiasPensum;

    public MateriasFormFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materiasPensum = consultarMateriasPensum();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.materias_form_fragment, container, false);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        setBarTitle();
        initEvents();
        return binding.getRoot();
    }

    public void initEvents() {
        buildAutocompleteMateriasPensum();
    }

    private void buildAutocompleteMateriasPensum() {
        AutoCompleteTextView autoCompleteMateriasPensum = binding.getRoot().findViewById(R.id.autoCompleteMateriasPensum);
        List<String> materias = materiasPensum.stream().map(MateriaPensum::getName).collect(Collectors.toList());

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, materias);
        autoCompleteMateriasPensum.setAdapter(adaptador);
    }

    private List<MateriaPensum> consultarMateriasPensum() {
        return Datos.getMateriasPensum();
    }

    private void setBarTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setTitle(R.string.app_bar_materias);
        }
    }
}
