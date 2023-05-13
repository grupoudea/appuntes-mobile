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

import java.util.ArrayList;
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
    private List<MateriaUniversidad> materiasUniversidad = new ArrayList<>();
    private AutoCompleteTextView autoCompleteMateriasUniversidad;
    private ArrayAdapter<MateriaUniversidad> adaptadorMateriasUniversidad;
    private MateriaUniversidad materiaSeleccionada;
    private Materia materia = new Materia();
    private boolean esEditar = false;
    private EditText editTextCreditos;
    private EditText editTextProfesor;

    public MateriasFormFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultarMateriasUniversidad("");
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
            if (esEditar) {
                if (buildMateria(materia)) {
                    actualizarMateria(materia);
                    NavHostFragment
                            .findNavController(MateriasFormFragment.this)
                            .navigate(R.id.action_materiasFormFragment_to_materiasFragment);
                }
            } else {
                Materia materiaNew = new Materia();
                if (buildMateria(materiaNew)) {
                    guardarNuevaMateria(materiaNew);
                    NavHostFragment
                            .findNavController(MateriasFormFragment.this)
                            .navigate(R.id.action_materiasFormFragment_to_materiasFragment);
                }
            }
        });

        if (getArguments() != null) {
            esEditar = (boolean) getArguments().getSerializable("esEditar");
            if (esEditar) {
                materia = (Materia) getArguments().getSerializable("materia");
                setMateria();
            }
        }
    }

    public void initEvents() {
        editTextCreditos = binding.getRoot().findViewById(R.id.editTextCreditos);
        editTextProfesor = binding.getRoot().findViewById(R.id.editTextProfesor);
        buildAutocompleteMateriasUniversidad();
    }

    private void buildAutocompleteMateriasUniversidad() {
        autoCompleteMateriasUniversidad = binding.getRoot().findViewById(R.id.autoCompleteMateriasUniversidad);
        List<MateriaUniversidad> materias = materiasUniversidad;
        adaptadorMateriasUniversidad = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, materias);
        autoCompleteMateriasUniversidad.setAdapter(adaptadorMateriasUniversidad);

        autoCompleteMateriasUniversidad.setOnItemClickListener((parent, view, position, id) -> {
            materiaSeleccionada = (MateriaUniversidad) parent.getItemAtPosition(position);
        });
    }

    private void consultarMateriasUniversidad(String materia) {
        Call<StandardResponse<List<MateriaUniversidad>>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class).buscarMateriasUniversidad(materia);

        call.enqueue(new Callback<StandardResponse<List<MateriaUniversidad>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<MateriaUniversidad>>> call, Response<StandardResponse<List<MateriaUniversidad>>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    List<MateriaUniversidad> materiaUniversidadList = response.body().getBody();
                    materiasUniversidad.clear();
                    materiasUniversidad.addAll(materiaUniversidadList);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<List<MateriaUniversidad>>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMateria() {
        if (Objects.nonNull(materia.getCreditos())) {
            editTextCreditos.setText(String.valueOf(materia.getCreditos()));
        }
        if (Objects.nonNull(materia.getProfesor())) {
            editTextProfesor.setText(String.valueOf(materia.getProfesor()));
        }
        if (Objects.nonNull(materia.getMateriaUniversidad())) {
            materiaSeleccionada = materia.getMateriaUniversidad();
            autoCompleteMateriasUniversidad.setText(materia.getMateriaUniversidad().getMateria());

            adaptadorMateriasUniversidad.notifyDataSetChanged();
            autoCompleteMateriasUniversidad.postDelayed(() -> {
                ArrayAdapter<MateriaUniversidad> nuevoAdaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, materiasUniversidad);
                autoCompleteMateriasUniversidad.setAdapter(nuevoAdaptador);
                nuevoAdaptador.notifyDataSetChanged();
            }, 100);
        }
    }

    public boolean buildMateria(Materia materia) {
        if (!validateRequired(materiaSeleccionada)) {
            return false;
        }
        materia.setIdMateriaFk(materiaSeleccionada.getId());
        materia.setIdEstudianteFk(Datos.getEstudianteSession());
        materia.setCreditos(getCreditos());
        if (!validateRequired(materia.getCreditos())) {
            return false;
        }
        materia.setProfesor(getProfesor());
        return true;
    }

    private Integer getCreditos() {
        String strCreditos = editTextCreditos.getText().toString();

        if (strCreditos.isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(strCreditos);
        }
    }

    private String getProfesor() {
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
                } else {
                    Toast.makeText(getActivity(), R.string.mensaje_crear_materia_error, Toast.LENGTH_SHORT).show();
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

    private void actualizarMateria(Materia materia) {
        Call<StandardResponse<Materia>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class).editarMateria(materia);

        call.enqueue(new Callback<StandardResponse<Materia>>() {
            @Override
            public void onResponse(Call<StandardResponse<Materia>> call, Response<StandardResponse<Materia>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    Materia materiaResponse = response.body().getBody();
                    Toast.makeText(getActivity(), R.string.mensaje_editar_materia_exito, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), R.string.mensaje_editar_materia_error, Toast.LENGTH_SHORT).show();
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
