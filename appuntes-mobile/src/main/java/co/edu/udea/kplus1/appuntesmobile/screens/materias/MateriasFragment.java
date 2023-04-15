package co.edu.udea.kplus1.appuntesmobile.screens.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;

public class MateriasFragment extends Fragment {

    private MateriasFragmentBinding binding;
    private MateriasViewModel viewModel;

    public MateriasFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.materias_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(MateriasViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setBarTitle();
        consultarMaterias();

        return binding.getRoot();
    }

    private void consultarMaterias() {
        ListView listview;
        List<Materia> materias = Datos.getMaterias();

        listview = (ListView) binding.getRoot().findViewById(R.id.listViewMaterias);

        MateriaAdapter adapter = new MateriaAdapter(requireContext(), materias);

        listview.setAdapter(adapter);

    }

    private void setBarTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setTitle(R.string.app_bar_materias);
        }
    }
}
