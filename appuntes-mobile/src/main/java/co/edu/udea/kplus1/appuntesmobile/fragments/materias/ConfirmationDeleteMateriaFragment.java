package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.MateriasServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.MateriasViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationDeleteMateriaFragment extends DialogFragment {

    private static final String TAG = "ConfirmationDeleteMateriaFragment";

    private Materia materia;
    private MateriasViewModel viewModel;

    public ConfirmationDeleteMateriaFragment() {

    }

    public static ConfirmationDeleteMateriaFragment confirmationDeleteMateria(Materia materia) {
        ConfirmationDeleteMateriaFragment fragment = new ConfirmationDeleteMateriaFragment();
        Bundle args = new Bundle();
        args.putSerializable("my_object", materia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            materia = (Materia) getArguments().getSerializable("my_object");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_confim_delete_materia)
                .setPositiveButton(R.string.delete, (dialog, id) -> {
                    materia = (Materia) getArguments().getSerializable("my_object");
                    eliminarMateria(materia);
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {

                });
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.materias_form_fragment, container, false);

        binding.setLifecycleOwner(getViewLifecycleOwner());

        viewModel = new ViewModelProvider(requireActivity()).get(MateriasViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    private void eliminarMateria(Materia materia) {
        Call<StandardResponse<Void>> call = RestApiClient.getClient()
                .create(MateriasServiceClient.class).eliminarMateria(materia.getId());

        call.enqueue(new Callback<StandardResponse<Void>>() {
            @Override
            public void onResponse(Call<StandardResponse<Void>> call, Response<StandardResponse<Void>> response) {
                viewModel.set(new Materia());
            }

            @Override
            public void onFailure(Call<StandardResponse<Void>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
            }
        });
    }
}