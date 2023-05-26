package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriaAdapter;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriasFragmentDirections;
import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class ApunteAdapter extends RecyclerView.Adapter<ApunteAdapter.ViewHolder> {

    private List<Apunte> mApuntesList=new ArrayList<>();
    private static List<Apunte> apuntes = new ArrayList<>();
    private static NavController navController;
    public ApunteAdapter(List<Apunte> apuntes, NavController navController) {
        ApunteAdapter.apuntes = apuntes;
        ApunteAdapter.navController = navController;
    }


    public ApunteAdapter(List<Apunte> apuntesList) {
        mApuntesList = apuntesList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewContenido;
        private final Button buttonMenuApunte;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Apunte apunte = getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("apunte", apunte);

                }
            });
            textViewContenido = v.findViewById(R.id.textViewContenido);

            buttonMenuApunte = v.findViewById(R.id.buttonMenuApunte);

            buttonMenuApunte.setOnClickListener(view -> {
                showMenu(view.getContext());
            });
        }

        public TextView getTextViewContenido() {
            return textViewContenido;
        }
        public Button getButtonMenuApunte() {
            return buttonMenuApunte;
        }

        private void showMenu(Context context) {
            PopupMenu popup = new PopupMenu(context, getButtonMenuApunte());
            popup.getMenuInflater().inflate(R.menu.menu_opciones_apunte, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                int position = getAdapterPosition();
                Apunte apunte = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("apunte", apunte);

                switch (item.getItemId()) {
                    case R.id.menuCompartirApunte:
                        if (position != RecyclerView.NO_POSITION) {

                        }
                        return true;
                    case R.id.menuPublicarApunte:

                    default:
                        return false;
                }
            });
            popup.show();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apunte_item, viewGroup, false);
        return new ApunteAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewContenido().setText(getItem(position).getContenido());

    }

    @Override
    public int getItemCount() {
        return mApuntesList.size();
    }

    public static Apunte getItem(int position) {
        return apuntes.get(position);
    }


}
