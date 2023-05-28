package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.database.UsuarioPersistence;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFragmentBinding;
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

public class MateriasFragment extends Fragment {
    private UsuarioManager usuarioManager;
    private UsuarioPersistence usuarioPersistence;
    private static final String TAG = "MateriasFragment";
    private MateriasFragmentBinding binding;
    private MateriasViewModel viewModel;
    private List<Materia> materias = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected MateriaAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioManager = UsuarioManager.getInstance(requireContext());
        usuarioPersistence = usuarioManager.obtenerUsuarioLogueado();
        Log.i(TAG, usuarioPersistence.toString());
        consultarMaterias("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.materias_fragment, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MateriasViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        buildReciclerView(savedInstanceState);

        viewModel.get().observe(getViewLifecycleOwner(), newData -> consultarMaterias(""));

        return binding.getRoot();
    }

    public void buildReciclerView(Bundle savedInstanceState) {
        mRecyclerView = binding.getRoot().findViewById(R.id.recyclerViewMaterias);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        if (mCurrentLayoutManagerType == null) {
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        NavController navController = NavHostFragment.findNavController(MateriasFragment.this);
        mAdapter = new MateriaAdapter(materias, navController);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCrearMateria.setOnClickListener(v -> NavHostFragment.findNavController(MateriasFragment.this)
                .navigate(R.id.action_materiasFragment_to_materiasFormFragment));
        initOnChangeBusqueda();
    }

    private void initOnChangeBusqueda() {
        EditText editTextBuscar = (EditText) binding.getRoot().findViewById(R.id.editTextBuscar);

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                consultarMaterias(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void consultarMaterias(String busqueda) {
        Call<StandardResponse<List<Materia>>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class)
                .filtrarMateriasPorEstudiante(busqueda, Math.toIntExact(usuarioPersistence.getIdEstudianteFk()));

        call.enqueue(new Callback<StandardResponse<List<Materia>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<Materia>>> call, Response<StandardResponse<List<Materia>>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    List<Materia> materiasList = response.body().getBody();

                    materias.clear();
                    materias.addAll(materiasList);

                }
                mAdapter = new MateriaAdapter(materias);
                mAdapter.setLoading(false);
                if (mRecyclerView != null) {
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<List<Materia>>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                mAdapter.setLoading(false);
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
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}
