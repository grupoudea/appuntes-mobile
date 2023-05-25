package co.edu.udea.kplus1.appuntesmobile.fragments.grupoApuntes;

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
import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.ApuntesServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.viewModel.GrupoApunteViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationDeleteGrupoApunteFragment extends DialogFragment {

    private static final String TAG = "ConfirmationDeleteGrupoApunteFragment";

    private GrupoApunte grupoApunte;
    private GrupoApunteViewModel viewModel;

    public ConfirmationDeleteGrupoApunteFragment() {

    }

    public static ConfirmationDeleteGrupoApunteFragment confirmationDeleteGrupoApunteFragment(GrupoApunte grupoApunte) {
        ConfirmationDeleteGrupoApunteFragment fragment = new ConfirmationDeleteGrupoApunteFragment();
        Bundle args = new Bundle();
        args.putSerializable("my_object", grupoApunte);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            grupoApunte = (GrupoApunte) getArguments().getSerializable("my_object");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_confim_delete_grupo_apunte)
                .setPositiveButton(R.string.delete, (dialog, id) -> {
                    grupoApunte = (GrupoApunte) getArguments().getSerializable("my_object");
                    eliminarGrupoApunte(grupoApunte);
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

        viewModel = new ViewModelProvider(requireActivity()).get(GrupoApunteViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        return binding.getRoot();
    }

    private void eliminarGrupoApunte(GrupoApunte grupoApunte) {
        Call<StandardResponse<Void>> call = RestApiClient.getClient()
                .create(ApuntesServiceClient.class).eliminarGruposApuntes(grupoApunte.getId());

        call.enqueue(new Callback<StandardResponse<Void>>() {
            @Override
            public void onResponse(Call<StandardResponse<Void>> call, Response<StandardResponse<Void>> response) {
                viewModel.set(new GrupoApunte());
            }

            @Override
            public void onFailure(Call<StandardResponse<Void>> call, Throwable t) {
                Log.i(TAG, "Error:" + t.getLocalizedMessage());
                Log.i(TAG, "Error:" + t.fillInStackTrace());
            }
        });
    }
}