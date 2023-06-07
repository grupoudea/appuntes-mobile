package co.edu.udea.kplus1.appuntesmobile.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriasViewModel extends ViewModel {
    private MutableLiveData<ConsultarMateriasResult> consultarMateriasResult;


    public void setNuevaMateria(Materia nuevaMateria) {
        ConsultarMateriasResult result = consultarMateriasResult.getValue();
        List<Materia> materiasList = result.getMaterias();
        materiasList.add(nuevaMateria);
        result = new ConsultarMateriasResult(false, materiasList, null);
        consultarMateriasResult.setValue(result);
    }

    public void editarMateria(Materia materiExistente) {
        ConsultarMateriasResult result = consultarMateriasResult.getValue();
        List<Materia> materiasList = result.getMaterias();
        materiasList = materiasList.stream().peek((materia)-> {
            if (materia.getId().equals(materiExistente.getId())){
                materia.setCreditos(materiExistente.getCreditos());
                materia.setProfesor(materiExistente.getProfesor());
            }
        }).collect(Collectors.toList());
        result = new ConsultarMateriasResult(false, materiasList, null);
        consultarMateriasResult.setValue(result);
    }
    public void eliminarMateria(Materia materia) {
        ConsultarMateriasResult result = consultarMateriasResult.getValue();
        if (result != null) {
            List<Materia> materiasList = result.getMaterias();
            materiasList.remove(materia);
            result = new ConsultarMateriasResult(false, materiasList, null);
            consultarMateriasResult.setValue(result);
        }
    }

    public LiveData<ConsultarMateriasResult> getConsultarMateriasResult() {
        if (consultarMateriasResult == null) {
            consultarMateriasResult = new MutableLiveData<>();
            consultarMaterias("");
        }

        return consultarMateriasResult;
    }


    public void consultarMaterias(String busqueda) {
        // Indica que la carga está en progreso
        consultarMateriasResult.setValue(new ConsultarMateriasResult(true, null, null));

        Call<StandardResponse<List<Materia>>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class)
                .filtrarMateriasPorEstudiante(busqueda, Math.toIntExact(1));

        call.enqueue(new Callback<StandardResponse<List<Materia>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<Materia>>> call, Response<StandardResponse<List<Materia>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Materia> materiasList = response.body().getBody();
                    // Actualiza el resultado con los datos obtenidos
                    consultarMateriasResult.setValue(new ConsultarMateriasResult(false, materiasList, null));

                } else {
                    // Manejo del error en caso de respuesta no exitosa
                    String errorMessage = response.message();
                    consultarMateriasResult.setValue(new ConsultarMateriasResult(false, null, errorMessage));
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<List<Materia>>> call, Throwable t) {
                // Manejo del error en caso de fallo en la llamada
                String errorMessage = t.getLocalizedMessage();
                consultarMateriasResult.setValue(new ConsultarMateriasResult(false, null, errorMessage));
            }
        });
    }


    public MateriasViewModel() {

    }
}
