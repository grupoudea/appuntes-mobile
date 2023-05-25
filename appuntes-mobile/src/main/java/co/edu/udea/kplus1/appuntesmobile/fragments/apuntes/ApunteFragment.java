package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.ApuntesServiceClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
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
    private List<GrupoApunte> grupoApuntes = new ArrayList<>();

    public ApunteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_apunte, container, false);


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

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apunte apunte=new Apunte(grupoApunte.getId(),mEditText.getText().toString());
                guardarApunte(apunte);
                mEditText.getText().clear();
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            grupoApunte = (GrupoApunte) getArguments().getSerializable("GrupoApunte");
            consultarGruposApuntes("");
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
    private void consultarGruposApuntes(String apunte) {
        Call<StandardResponse<List<GrupoApunte>>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).buscarGrupoApunte(apunte);

        call.enqueue(new Callback<StandardResponse<List<GrupoApunte>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<GrupoApunte>>> call, Response<StandardResponse<List<GrupoApunte>>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    List<GrupoApunte> grupoApuntesList = response.body().getBody();
                    grupoApuntes.clear();
                    grupoApuntes.addAll(grupoApuntesList);
                }
            }

            @Override
            public void onFailure(Call<StandardResponse<List<GrupoApunte>>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
                Toast.makeText(getActivity(), "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
        
}

