package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import co.edu.udea.kplus1.appuntesmobile.databinding.FragmentApunteBinding;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriaAdapter;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriasFragment;
import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.ApuntesServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.ApunteViewModel;
import co.edu.udea.kplus1.appuntesmobile.viewModel.MateriasViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApunteFragment extends Fragment {

    private static final String TAG = "ApuntesFragment";
    private FragmentApunteBinding binding;
    private ApunteViewModel viewModel;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private ImageButton mCameraButton;
    private ImageButton mMicButton;
    private ImageButton mSendButton;
    private List<Apunte> mApuntesList = new ArrayList<>();
    private ApunteAdapter mApunteAdapter;
    private GrupoApunte grupoApunte;
    private List<Apunte> apuntes = new ArrayList<>();
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;

    public ApunteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultarApuntes("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apunte, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_apunte, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ApunteViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        buildReciclerView(savedInstanceState);

        viewModel.get().observe(getViewLifecycleOwner(), newData -> consultarApuntes(""));

        mRecyclerView = view.findViewById(R.id.cyclerViewApuntes);
        mEditText = view.findViewById(R.id.editText);
        mCameraButton = view.findViewById(R.id.cameraButton);
        mMicButton = view.findViewById(R.id.micButton);
        mSendButton = view.findViewById(R.id.sendButton);

        // Initialize RecyclerView and Adapter
        mApunteAdapter = new ApunteAdapter(mApuntesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mApunteAdapter);




        // Set click listeners for buttons
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Foto
            }
        });

        mMicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Audio
            }
        });

        binding.sendButton.setOnClickListener(v ->  {
            System.out.println("1"+mEditText.getText().toString());
            System.out.println("2"+grupoApunte.getId());
            Apunte apunte = new Apunte(grupoApunte.getId(), mEditText.getText().toString().trim());
            System.out.println("3"+apunte.toString());

            guardarApunte(apunte);
            mEditText.getText().clear();

        });



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            grupoApunte = (GrupoApunte) getArguments().getSerializable("GrupoApunte");
            consultarApuntes("");
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
                if (mRecyclerView != null) {
                    mRecyclerView.setAdapter(mApunteAdapter);
                }
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
        NavController navController = NavHostFragment.findNavController(ApunteFragment.this);
        mApunteAdapter = new ApunteAdapter(apuntes, navController);
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

