package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;
import java.util.Objects;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFormFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriasFormFragment extends Fragment {

    private static final String TAG = "MateriasFormFragment";

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

        initEvents();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonGuardar.setOnClickListener(v -> {
            guardarMateria();
            NavHostFragment.findNavController(MateriasFormFragment.this).navigate(R.id.action_materiasFormFragment_to_materiasFragment);
        });
    }

    public void initEvents() {
        buildAutocompleteMateriasUniversidad();
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
        guardarNuevaMateria(materia);
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

    private void guardarNuevaMateria(Materia materia) {
        Call<StandardResponse<Materia>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class).guardarMateria(materia);

        call.enqueue(new Callback<StandardResponse<Materia>>() {
            @Override
            public void onResponse(Call<StandardResponse<Materia>> call, Response<StandardResponse<Materia>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    Materia materiaResponse = response.body().getBody();
                    Toast.makeText(getActivity(), R.string.mensaje_crear_materia_exito, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<Materia>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateRequired(Object object) {
        if (Objects.isNull(object)) {
            Toast.makeText(getActivity(), R.string.mensaje_campo_requerido, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
