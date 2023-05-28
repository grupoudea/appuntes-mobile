package co.edu.udea.kplus1.appuntesmobile.fragments.materiasPublicas;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.database.UsuarioPersistence;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasPublicasFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriaAdapter;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.utils.UsuarioManager;
import co.edu.udea.kplus1.appuntesmobile.viewModel.MateriasViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriasPublicasFragment extends Fragment {

    private static final String TAG = "MateriasPublicasFragment";
    private static final int SPAN_COUNT = 2;

    private static final Integer ID_ESTUDIANTE_PUBLICO = 6;
    private MateriasPublicasFragmentBinding materiasPublicasFragmentBinding;
    private MateriasViewModel viewModel;
    protected RecyclerView mRecyclerView;
    protected MateriaAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private UsuarioManager usuarioManager;

    private UsuarioPersistence usuarioPersistence;

    private List<Materia> materias = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioManager = UsuarioManager.getInstance(requireContext());
        usuarioPersistence = usuarioManager.obtenerUsuarioLogueado();
        consultarMaterias("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        materiasPublicasFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.materias_publicas_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(MateriasViewModel.class);
        materiasPublicasFragmentBinding.setLifecycleOwner(getViewLifecycleOwner());
        buildReciclerView(savedInstanceState);
        consultarMaterias("");
        return materiasPublicasFragmentBinding.getRoot();
    }

    public void buildReciclerView(Bundle savedInstanceState) {
        mRecyclerView = materiasPublicasFragmentBinding.getRoot().findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new MateriaAdapter(materias);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void consultarMaterias(String busqueda) {

        Call<StandardResponse<List<Materia>>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class)
                .filtrarMateriasPorEstudiante(busqueda, ID_ESTUDIANTE_PUBLICO);
        call.enqueue(new Callback<StandardResponse<List<Materia>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<Materia>>> call, Response<StandardResponse<List<Materia>>> response) {
                List<Materia> materiasList = response.body().getBody();

                materias.clear();
                materias.addAll(materiasList);
                
                mAdapter = new MateriaAdapter(materias);
                mAdapter.setLoading(false);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<StandardResponse<List<Materia>>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                mAdapter.setLoading(false);
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

}