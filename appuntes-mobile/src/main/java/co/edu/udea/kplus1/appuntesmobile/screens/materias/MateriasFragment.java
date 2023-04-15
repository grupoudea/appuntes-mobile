package co.edu.udea.kplus1.appuntesmobile.screens.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFragmentBinding;

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

        return binding.getRoot();
    }

    private void setBarTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setTitle(R.string.app_bar_materias);
        }
    }
}
