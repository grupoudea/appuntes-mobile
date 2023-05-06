package co.edu.udea.kplus1.appuntesmobile.fragments.grupoApuntes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    //TODO REDIRIGIR AL CHAT DE APUNTES
                }
            });
            textViewNombreGrupoApunte = v.findViewById(R.id.textViewNombreGrupoApunte);
            textViewFechaGrupoApunte = v.findViewById(R.id.textViewFechaGrupoApunte);
        }

        public TextView getTextViewNombreGrupoApunte() {
            return textViewNombreGrupoApunte;
        }

        public TextView getTextViewFechaGrupoApunte() {
            return textViewFechaGrupoApunte;
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

}