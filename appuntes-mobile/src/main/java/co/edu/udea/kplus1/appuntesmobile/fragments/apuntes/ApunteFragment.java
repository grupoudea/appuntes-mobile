package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.Objects;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.FragmentApunteBinding;
import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.ApuntesServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.ApunteViewModel;
import co.edu.udea.kplus1.appuntesmobile.viewModel.GrupoApunteViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApunteFragment extends Fragment {

    private static final String TAG = "ApuntesFragment";
    private FragmentApunteBinding binding;
    private ApunteViewModel viewModel;
    private GrupoApunteViewModel grupoApunteViewModel;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private EditText editTextNombreApunte;
    private ImageButton saveGrupo;
    private TextView textViewTituloGrupoapunte;
    private ApunteAdapter mApunteAdapter;
    private GrupoApunte grupoApunte = new GrupoApunte();
    private List<Apunte> apuntes = new ArrayList<>();
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;
    private Materia materia = new Materia();

    public ApunteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultarApuntes("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_apunte, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ApunteViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        buildReciclerView(savedInstanceState);
        viewModel.get().observe(getViewLifecycleOwner(), newData -> consultarApuntes(""));

        grupoApunteViewModel = new ViewModelProvider(requireActivity()).get(GrupoApunteViewModel.class);
        grupoApunteViewModel.get().observe(getViewLifecycleOwner(), newData -> {
            grupoApunte = newData;
        });

        mEditText = binding.getRoot().findViewById(R.id.editText);

        editTextNombreApunte = binding.getRoot().findViewById(R.id.editTextNombreApunte);
        saveGrupo = binding.getRoot().findViewById(R.id.buttonSaveGrupo);
        textViewTituloGrupoapunte = binding.getRoot().findViewById(R.id.textViewTituloGrupoapunte);

        // Set click listeners for buttons
        binding.getRoot().findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Foto
            }
        });

        binding.getRoot().findViewById(R.id.micButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Audio
            }
        });

        binding.getRoot().findViewById(R.id.sendButton).setOnClickListener(v -> {
            Apunte apunte = new Apunte(grupoApunte.getId(), mEditText.getText().toString().trim());
            guardarApunte(apunte);
            mEditText.getText().clear();

        });

        binding.buttonSaveGrupo.setOnClickListener(v -> {
            GrupoApunte grupoApunte = new GrupoApunte();
            grupoApunte.setIdMateriaFk(materia.getId());

            if (Objects.isNull(editTextNombreApunte.getText()) || editTextNombreApunte.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), R.string.mensaje_campo_requerido, Toast.LENGTH_SHORT).show();
                return;
            }
            grupoApunte.setNombre(editTextNombreApunte.getText().toString());
            guardarGrupoApunte(grupoApunte);

        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            boolean esNuevoGrupo = (boolean) getArguments().getSerializable("esNuevoGrupo");
            if (esNuevoGrupo) {
                materia = (Materia) getArguments().getSerializable("Materia");
                mostrarTitulo(false);
            } else {
                GrupoApunte grupoApunteSelected = (GrupoApunte) getArguments().getSerializable("GrupoApunte");
                grupoApunteViewModel.set(grupoApunteSelected);
                grupoApunte = grupoApunteSelected;
                mostrarTitulo(true);
                consultarApuntes("");
            }
        }
    }

    private void mostrarTitulo(boolean mostrar) {
        if (mostrar) {
            setTituloGrupoApunte();
            editTextNombreApunte.setVisibility(View.INVISIBLE);
            saveGrupo.setVisibility(View.INVISIBLE);
            textViewTituloGrupoapunte.setVisibility((View.VISIBLE));
        } else {
            editTextNombreApunte.setVisibility(View.VISIBLE);
            saveGrupo.setVisibility(View.VISIBLE);
            textViewTituloGrupoapunte.setVisibility((View.INVISIBLE));
        }
    }

    private void setTituloGrupoApunte() {
        if (Objects.nonNull(grupoApunte.getNombre())) {
            textViewTituloGrupoapunte.setText(grupoApunte.getNombre());
        } else {
            textViewTituloGrupoapunte.setText(editTextNombreApunte.getText());
        }
    }

    private void guardarApunte(Apunte apunte) {
        Call<StandardResponse<Apunte>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).guardarApunte(apunte);

        call.enqueue(new Callback<StandardResponse<Apunte>>() {
            @Override
            public void onResponse(Call<StandardResponse<Apunte>> call, Response<StandardResponse<Apunte>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    Apunte apunteResponse = response.body().getBody();
                    viewModel.set(apunteResponse);
                    Toast.makeText(getActivity(), R.string.mensaje_guardar_apunte_exito, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), R.string.mensaje_guardar_apunte_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<Apunte>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarApuntes(String apunte) {
        Call<StandardResponse<List<Apunte>>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).buscarApuntesPorFiltro(apunte, grupoApunte.getId());

        call.enqueue(new Callback<StandardResponse<List<Apunte>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<Apunte>>> call, Response<StandardResponse<List<Apunte>>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    List<Apunte> apunteList = response.body().getBody();
                    apuntes.clear();
                    apuntes.addAll(apunteList);
                }
                mApunteAdapter = new ApunteAdapter(apuntes);
                mRecyclerView.setAdapter(mApunteAdapter);
            }

            @Override
            public void onFailure(Call<StandardResponse<List<Apunte>>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buildReciclerView(Bundle savedInstanceState) {
        mRecyclerView = binding.getRoot().findViewById(R.id.cyclerViewApuntes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        if (mCurrentLayoutManagerType == null) {
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mApunteAdapter = new ApunteAdapter(apuntes);
        mRecyclerView.setAdapter(mApunteAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
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

    private void guardarGrupoApunte(GrupoApunte grupoApunte) {
        Call<StandardResponse<GrupoApunte>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).crearGruposApuntes(grupoApunte);

        call.enqueue(new Callback<StandardResponse<GrupoApunte>>() {
            @Override
            public void onResponse(Call<StandardResponse<GrupoApunte>> call, Response<StandardResponse<GrupoApunte>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    GrupoApunte grupoApunte = response.body().getBody();
                    grupoApunteViewModel.set(grupoApunte);
                    mostrarTitulo(true);
                    Toast.makeText(getActivity(), R.string.mensaje_crear_grupo_exito, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), R.string.mensaje_crear_grupo_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<GrupoApunte>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

