package co.edu.udea.kplus1.appuntesmobile.fragments.grupoApuntes;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.FragmentGrupoApunteBinding;
import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.ApuntesServiceClient;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.GrupoApunteViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoApunteFragment extends Fragment {

    private static final String TAG = "GrupoApunteFragment";
    private FragmentGrupoApunteBinding binding;
    private GrupoApunteViewModel viewModel;
    private List<GrupoApunte> grupoApuntes = new ArrayList<>();
    protected RecyclerView mRecyclerView;
    protected GrupoApunteAdapter grupoApunteAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;
    private Materia materia = new Materia();

    public GrupoApunteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_grupo_apunte, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(GrupoApunteViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        buildReciclerView(savedInstanceState);

        viewModel.get().observe(getViewLifecycleOwner(), newData -> consultarGruposApuntes(""));

        buildReciclerView(savedInstanceState);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            materia = (Materia) getArguments().getSerializable("materia");
            setTituloMateria();
            consultarGruposApuntes("");
        }
        initOnChangeBusqueda();
        FloatingActionButton buttonCrearGrupoApunte = view.findViewById(R.id.buttonCrearGrupoApunte);
        buttonCrearGrupoApunte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.apuntes_layout);
            }
        });
        Button buttonMenuGrupoApunte = view.findViewById(R.id.buttonMenuGrupoApunte);
        buttonMenuGrupoApunte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }


    public void showPopupMenu(View view) {
        Context context = view.getContext();
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Manejar la opción seleccionada del menú desplegable
                switch (item.getItemId()) {
                    case R.id.option1:
                        eliminarElemento();
                        return true;
                    case R.id.option2:
                        // Acción para la opción 2
                        return true;
                    case R.id.option3:
                        // Acción para la opción 3
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    private void eliminarElemento() {
       if (!grupoApuntes.isEmpty()) {
            grupoApuntes.remove(0);
            grupoApunteAdapter.notifyDataSetChanged(); // Actualizar la vista después de eliminar el elemento
        }
    }




    private void setTituloMateria() {
        TextView viewById = (TextView) binding.getRoot().findViewById(R.id.textViewTituloMateria);
        viewById.setText(materia.getMateriaUniversidad().getMateria());
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void buildReciclerView(Bundle savedInstanceState) {
        mRecyclerView = binding.getRoot().findViewById(R.id.recyclerViewGrupoApuntes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        if (mCurrentLayoutManagerType == null) {
            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        grupoApunteAdapter = new GrupoApunteAdapter(grupoApuntes);
        mRecyclerView.setAdapter(grupoApunteAdapter);
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

    private void initOnChangeBusqueda() {
        EditText editTextBuscar = (EditText) binding.getRoot().findViewById(R.id.editTextBuscarGrupoApunte);

        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                consultarGruposApuntes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void consultarGruposApuntes(String busqueda) {
        Call<StandardResponse<List<GrupoApunte>>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).filtrarGrupoApuntesPorMateria(busqueda, materia.getId(), Datos.getEstudianteSession());

        call.enqueue(new Callback<StandardResponse<List<GrupoApunte>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<GrupoApunte>>> call, Response<StandardResponse<List<GrupoApunte>>> response) {
                if (Objects.nonNull(response) && Objects.nonNull(response.body()) && Objects.nonNull(response.body().getBody())) {
                    List<GrupoApunte> grupoApunteList = response.body().getBody();
                    grupoApuntes.clear();
                    grupoApuntes.addAll(grupoApunteList);
                }
                grupoApunteAdapter = new GrupoApunteAdapter(grupoApuntes);
                mRecyclerView.setAdapter(grupoApunteAdapter);
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