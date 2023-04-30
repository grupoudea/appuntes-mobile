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
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasPublicasFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriaAdapter;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.MateriasViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MateriasPublicasFragment extends Fragment {

    private MateriasPublicasFragmentBinding materiasPublicasFragmentBinding;
    private MateriasViewModel viewModel;
    protected RecyclerView mRecyclerView;
    protected MateriaAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;


    private List<Materia> materias = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        consultarMaterias();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        materiasPublicasFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.materias_publicas_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(MateriasViewModel.class);
        materiasPublicasFragmentBinding.setLifecycleOwner(getViewLifecycleOwner());
        buildReciclerView(savedInstanceState);

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

    private void consultarMaterias() {

        Call<StandardResponse<List<Materia>>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class).obtenerMateriasPorEstudiante(6);
        call.enqueue(new Callback<StandardResponse<List<Materia>>>() {
            @Override
            public void onResponse(Call<StandardResponse<List<Materia>>> call, Response<StandardResponse<List<Materia>>> response) {
                Log.i("RETRO ERROR3", "Entra");
                List<Materia> materiasList = response.body().getBody();

                for (Materia materia : materiasList) {
                    System.out.println("materia " + materia.getId());
                    materias.add(materia);
                }
                mAdapter = new MateriaAdapter(materias);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<StandardResponse<List<Materia>>> call, Throwable t) {
                Log.i("RETRO ERROR", "Error:" + t.getLocalizedMessage());
                Log.i("RETRO ERROR2", "Error:" + t.fillInStackTrace());

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