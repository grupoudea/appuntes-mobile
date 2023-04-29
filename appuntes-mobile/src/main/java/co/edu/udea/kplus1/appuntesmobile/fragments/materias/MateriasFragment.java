package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import static co.edu.udea.kplus1.appuntesmobile.utils.Constants.KEY_LAYOUT_MANAGER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.databinding.MateriasFragmentBinding;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.service.temp.Datos;
import co.edu.udea.kplus1.appuntesmobile.utils.LayoutManagerType;
import co.edu.udea.kplus1.appuntesmobile.viewModel.MateriasViewModel;

public class MateriasFragment extends Fragment {

    private MateriasFragmentBinding binding;
    private MateriasViewModel viewModel;
    private List<Materia> materias;
    protected RecyclerView mRecyclerView;
    protected MateriaAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private static final int SPAN_COUNT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materias = consultarMaterias();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.materias_fragment, container, false);

        viewModel = new ViewModelProvider(this).get(MateriasViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setBarTitle();
        buildReciclerView(savedInstanceState);
        return binding.getRoot();
    }

    public void buildReciclerView(Bundle savedInstanceState) {
        mRecyclerView = binding.getRoot().findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        mAdapter = new MateriaAdapter(materias);
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
    }

    private List<Materia> consultarMaterias() {
        return Datos.getMaterias();
    }

    private void setBarTitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setTitle(R.string.app_bar_materias);
        }
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
