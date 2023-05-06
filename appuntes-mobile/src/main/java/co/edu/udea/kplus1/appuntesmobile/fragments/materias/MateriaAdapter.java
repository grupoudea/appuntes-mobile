package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.ViewHolder> {

    private static final String TAG = "MateriaAdapter";
    private static List<Materia> materias = new ArrayList<>();

    public MateriaAdapter(List<Materia> materias) {
        MateriaAdapter.materias = materias;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNombreMateria;
        private final TextView textViewNombreProfesor;
        private final TextView textViewCreditos;
        private final Button buttonMenuMateria;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> {

                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Materia materia = getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("materia", materia);
                    NavDirections action = MateriasFragmentDirections.actionMateriasFragmentToGrupoApunteFragment(materia);
                    Navigation.findNavController(v).navigate(action);
                }
            });
            textViewNombreMateria = v.findViewById(R.id.textViewNombreMateria);
            textViewNombreProfesor = v.findViewById(R.id.textViewNombreProfesor);
            textViewCreditos = v.findViewById(R.id.textViewCreditos);
            buttonMenuMateria = v.findViewById(R.id.buttonMenuMateria);

            buttonMenuMateria.setOnClickListener(view -> {
                showMenu(view.getContext());
            });
        }

        public TextView getTextViewNombreMateria() {
            return textViewNombreMateria;
        }

        public TextView getTextViewNombreProfesor() {
            return textViewNombreProfesor;
        }

        public TextView getTextViewCreditos() {
            return textViewCreditos;
        }

        public Button getButtonMenuMateria() {
            return buttonMenuMateria;
        }

        private void showMenu(Context context) {
            PopupMenu popup = new PopupMenu(context, buttonMenuMateria);
            popup.getMenuInflater().inflate(R.menu.menu_opciones_materia, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menuEditar:
                        return true;
                    case R.id.menuEliminar:
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.materia_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextViewNombreMateria().setText(getItem(position).getMateriaUniversidad().getMateria());
        viewHolder.getTextViewNombreProfesor().setText(getItem(position).getProfesor());
        viewHolder.getTextViewCreditos().setText(String.valueOf(getItem(position).getCreditos()));
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }

    public static Materia getItem(int position) {
        return materias.get(position);
    }

}
