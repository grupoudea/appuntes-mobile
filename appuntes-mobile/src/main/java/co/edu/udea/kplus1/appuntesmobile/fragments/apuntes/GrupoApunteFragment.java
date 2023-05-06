package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.udea.kplus1.appuntesmobile.R;

public class GrupoApunteFragment extends Fragment {

    public GrupoApunteFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grupo_apunte, container, false);
    }
}