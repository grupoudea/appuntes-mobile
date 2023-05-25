package co.edu.udea.kplus1.appuntesmobile.fragments.grupoApuntes;


import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;


import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;

import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;


public class GrupoApunteAdapter extends RecyclerView.Adapter<GrupoApunteAdapter.ViewHolder> {

    private static final String TAG = "GrupoApunteAdapter";
    private static List<GrupoApunte> grupoApuntes = new ArrayList<>();

    public GrupoApunteAdapter(List<GrupoApunte> grupoApuntes) {
        GrupoApunteAdapter.grupoApuntes = grupoApuntes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNombreGrupoApunte;
        private final TextView textViewFechaGrupoApunte;
        private final Button buttonMenuGrupoApunte;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    GrupoApunte grupoApunte = getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("GrupoApunte", grupoApunte);
                    NavDirections action = co.edu.udea.kplus1.appuntesmobile.fragments.grupoApuntes.GrupoApunteFragmentDirections.actionGrupoApunteFragmentToFragmentApunte(grupoApunte);
                    Navigation.findNavController(v).navigate(action);
                }

            });
            textViewNombreGrupoApunte = v.findViewById(R.id.textViewNombreGrupoApunte);
            textViewFechaGrupoApunte = v.findViewById(R.id.textViewFechaGrupoApunte);
            buttonMenuGrupoApunte = v.findViewById(R.id.buttonMenuGrupoApunte);

            buttonMenuGrupoApunte.setOnClickListener(view -> {
                showMenu(view.getContext());
            });
        }

        public TextView getTextViewNombreGrupoApunte() {
            return textViewNombreGrupoApunte;
        }

        public TextView getTextViewFechaGrupoApunte() {
            return textViewFechaGrupoApunte;
        }

        public Button getButtonMenuGrupoApunte() {
            return buttonMenuGrupoApunte;
        }

        private void showMenu(Context context) {
            PopupMenu popup = new PopupMenu(context, getButtonMenuGrupoApunte());
            popup.getMenuInflater().inflate(R.menu.menu_opciones_grupo_apunte, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                int position = getAdapterPosition();
                GrupoApunte grupoApunte = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("grupoApunte", grupoApunte);

                switch (item.getItemId()) {
                    case R.id.menuEliminarGrupoApunte:
                        showMyDialogFragment(context, grupoApunte);
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grupo_apunte_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewNombreGrupoApunte().setText(getItem(position).getNombre());
        viewHolder.getTextViewFechaGrupoApunte().setText(getItem(position).getFechaCreacion());
    }

    @Override
    public int getItemCount() {
        return grupoApuntes.size();
    }

    public static GrupoApunte getItem(int position) {
        return grupoApuntes.get(position);
    }

    private static void showMyDialogFragment(Context context, GrupoApunte grupoApunte) {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        ConfirmationDeleteGrupoApunteFragment dialog = ConfirmationDeleteGrupoApunteFragment.confirmationDeleteGrupoApunteFragment(grupoApunte);
        dialog.show(fragmentManager, "my_dialog");
    }

}