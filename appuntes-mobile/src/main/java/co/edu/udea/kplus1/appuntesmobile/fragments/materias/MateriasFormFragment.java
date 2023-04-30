package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFormFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;

public class MateriasFormFragment extends Fragment {

    private MateriasFormFragmentBinding binding;
    private List<MateriaUniversidad> materiasUniversidad;
    private AutoCompleteTextView autoCompleteMateriasUniversidad;
    private MateriaUniversidad materiaSeleccionada;

    public MateriasFormFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materiasUniversidad = consultarMateriasUniversidad();
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
        buildAutocompleteMateriasUniversidad();

        Button buttonGuardar = (Button) binding.getRoot().findViewById(R.id.buttonGuardar);
        buttonGuardar.setOnClickListener(v -> guardarMateria());
    }

    private void buildAutocompleteMateriasUniversidad() {
        autoCompleteMateriasUniversidad = binding.getRoot().findViewById(R.id.autoCompleteMateriasUniversidad);
        List<MateriaUniversidad> materias = materiasUniversidad;
        ArrayAdapter<MateriaUniversidad> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, materias);
        autoCompleteMateriasUniversidad.setAdapter(adaptador);

        autoCompleteMateriasUniversidad.setOnItemClickListener((parent, view, position, id) -> {
            materiaSeleccionada = (MateriaUniversidad) parent.getItemAtPosition(position);
        });
    }

    private List<MateriaUniversidad> consultarMateriasUniversidad() {
        return Datos.getMateriasPensum();
    }

    public void guardarMateria() {
        Materia materia = new Materia();
        if (!validateRequired(materiaSeleccionada)) {
            return;
        }
        materia.setIdMateriaFk(materiaSeleccionada.getId());
        materia.setIdEstudianteFk(1);
        materia.setCreditos(getCreditos());
        materia.setProfesor(getProfesor());
    }

    private Integer getCreditos() {
        EditText editTextCreditos = binding.getRoot().findViewById(R.id.editTextCreditos);
        String strCreditos = editTextCreditos.getText().toString();

        if (strCreditos.isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(strCreditos);
        }
    }

    private String getProfesor() {
        EditText editTextProfesor = binding.getRoot().findViewById(R.id.editTextProfesor);
        String strProfesor = editTextProfesor.getText().toString();

        if (strProfesor.isEmpty()) {
            return null;
        } else {
            return strProfesor;
        }
    }

    private boolean validateRequired(Object object) {
        if (Objects.isNull(object)) {
            Toast.makeText(getActivity(), R.string.mensaje_campo_requerido, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setBarTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setTitle(R.string.app_bar_materias);
        }
    }
}
