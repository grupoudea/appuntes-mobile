package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFormFragmentBinding;

public class MateriasFormFragment extends Fragment {

    private MateriasFormFragmentBinding binding;

    public MateriasFormFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.materias_form_fragment, container, false);

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
